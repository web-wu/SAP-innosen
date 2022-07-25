package com.tabwu.SAP.seckills.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.constant.RabbitStaticConstant;
import com.tabwu.SAP.common.entity.MqMsg;
import com.tabwu.SAP.common.entity.R;
import com.tabwu.SAP.common.entity.SeckillMsg;
import com.tabwu.SAP.common.exception.CostomException;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.seckills.entity.Material;
import com.tabwu.SAP.seckills.entity.Sale;
import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.tabwu.SAP.seckills.entity.to.SeckillProductInfoTo;
import com.tabwu.SAP.seckills.entity.to.SeckillSessionTo;
import com.tabwu.SAP.seckills.entity.vo.SeckillParamsVo;
import com.tabwu.SAP.seckills.entity.vo.SeckillSessionVo;
import com.tabwu.SAP.seckills.feign.WareFeignService;
import com.tabwu.SAP.seckills.mapper.SeckillSessionMapper;
import com.tabwu.SAP.seckills.service.ISeckillProRelationService;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author tabwu
 * @since 2022-07-19
 */
@Service
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements ISeckillSessionService {

    @Autowired
    private ISeckillProRelationService seckillProRelationService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WareFeignService wareFeignService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String SECKILL_SESSION_KEY = "seckill:session_";

    private static final String SECKILL_SESSION_PRO_KEY = "seckill:session:pro";

    private static final String SECKILL_PRO_SEMAPHORE = "seckill:PRODUCT:SEMAPHORE_";


    // 根据描述场次id查询当前场次的秒杀商品信息
    @Override
    public List<SeckillProductInfoTo> findSeckillProducts(Long sessionId) {
        SeckillSession session = this.getById(sessionId);
        long startTime = session.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startEnd = session.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String key = SECKILL_SESSION_KEY + "_" + session.getId()+ "_" + startTime + "_" + startEnd;
        String str = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(str)) {
            ArrayList<SeckillProductInfoTo> productInfoTos = new ArrayList<>();
            BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_SESSION_PRO_KEY);
            String[] pids = str.replace("[","").replace("]","").replace(" ","").split(",");
            for (String pid : pids) {
                String keyName = session.getId() + "_" + pid;
                SeckillProductInfoTo productInfoTo = JSON.parseObject(operations.get(keyName), SeckillProductInfoTo.class);
                productInfoTos.add(productInfoTo);
            }
            return productInfoTos;
        }

        return null;
    }

    // 根据描述场次id和商品id查询商品详情信息
    @Override
    public SeckillProductInfoTo getSeckillProductByPid(Long sessionId, String pid) {
        BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_SESSION_PRO_KEY);
        String keyName = sessionId + "_" + pid;
        return JSON.parseObject(operations.get(keyName), SeckillProductInfoTo.class);
    }

    // 根据描述场次id和商品id以及商品随机码立即抢购
    @Override
    public String seckillPurchase(SeckillParamsVo seckillParamsVo,String uid) throws InterruptedException {
        // 2、合法性判断---时间、随机码、幂等性-是否重复购买、数量限制
        BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_SESSION_PRO_KEY);
        String keyName = seckillParamsVo.getSessionId() + "_" + seckillParamsVo.getPid();
        SeckillProductInfoTo productInfoTo = JSON.parseObject(operations.get(keyName), SeckillProductInfoTo.class);
        if (productInfoTo == null) {
            throw new CostomException(20004,"秒杀商品不存在");
        }
        if (!seckillParamsVo.getToken().equals(productInfoTo.getToken())) {
            throw new CostomException(20004,"秒杀商品随机码错误");
        }
        long now = System.currentTimeMillis();
        long startTime = productInfoTo.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startEnd = productInfoTo.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (now <= startTime && now >= startEnd) {
            throw new CostomException(20004,"当前时间该秒杀商品不能参与秒杀活动");
        }
        String semaphoreCount = redisTemplate.opsForValue().get(SECKILL_PRO_SEMAPHORE + seckillParamsVo.getToken());
        int count = Integer.parseInt(semaphoreCount);
        if (!(seckillParamsVo.getNum() > 0 && seckillParamsVo.getNum() <= productInfoTo.getSeckillLimit() && seckillParamsVo.getNum() <= count)) {
            throw new CostomException(20004,"该秒杀商品数量异常");
        }
        long ttl = startEnd - now;
        String userId_pid = uid + "-" + seckillParamsVo.getPid();
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(userId_pid, String.valueOf(seckillParamsVo.getNum()), ttl, TimeUnit.MILLISECONDS);
        if (!flag) {
            throw new CostomException(20004,"该用户已经抢购过该商品，不能重复购买");
        }
        // 3、获取抢购商品数量的信号量
        RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_PRO_SEMAPHORE + seckillParamsVo.getToken());
        if (!semaphore.tryAcquire(seckillParamsVo.getNum(), 100, TimeUnit.MILLISECONDS)) {
            throw new CostomException(20004,"该商品信号量不足，也就秒杀库存不足");
        }
        // 4、发送MQ消息快速创建订单
        return sendMsgToMqAutoGenerateSeckillSaleOrder(seckillParamsVo.getPid(),seckillParamsVo.getNum(),productInfoTo.getSeckillPrice());
    }

    // 发送MQ消息快速创建订单
    private String sendMsgToMqAutoGenerateSeckillSaleOrder(String pid, int num, BigDecimal price) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String code = "YLS-SALE-" + format.format(new Date()) + "-" + new Random().nextInt(100);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(code);
        SeckillMsg seckillMsg = new SeckillMsg();
        seckillMsg.setCode(code);
        seckillMsg.setPid(pid);
        seckillMsg.setNum(num);
        seckillMsg.setRice(price);
        seckillMsg.setTotalPrice(price.multiply(new BigDecimal(num)));
        seckillMsg.setPayStatus(false);
        rabbitTemplate.convertAndSend(RabbitStaticConstant.SECKILL_SUCCESS_QUEUE,"seckill.success",seckillMsg,correlationData);
        return code;
    }


    @Override
    public HashMap<String, Object> pageList(SeckillSessionVo seckillSessionVo) {
        Page<SeckillSession> page = new Page<>(seckillSessionVo.getCurrent(), seckillSessionVo.getSize());
        QueryWrapper<SeckillSession> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(seckillSessionVo.getName())) {
            queryWrapper.like("name",seckillSessionVo.getName());
        }

        this.page(page,queryWrapper);

        return (HashMap<String, Object>) PageUtil.queryPage(page);
    }

    @Override
    public List<SeckillProRelation> findSessionProsById(String id) {
        List<SeckillProRelation> proRelations = seckillProRelationService.list(new QueryWrapper<SeckillProRelation>().eq("promotion_session_id", id));
        return proRelations;
    }

    @Override
    public void uploadSessionsAndRelationProduct() {
        // 1、扫描最近3天内的秒杀场次
        List<SeckillSessionTo> seckillSessionTos = findSessionsLatest3Days();
        if (seckillSessionTos.size() > 0) {
            // 2、上架秒杀场次缓存与相应的商品id
            uploadSessionInfos(seckillSessionTos);
            // 3、上架秒杀场次关联的商品详情信息、随机码、活动时间
            uploadSessionRaletionProInfos(seckillSessionTos);
        }
    }

    // 3、上架秒杀场次关联的商品详情信息、随机码、活动时间
    private void uploadSessionRaletionProInfos(List<SeckillSessionTo> sessions) {
        for (SeckillSessionTo session : sessions) {
            BoundHashOperations<String, String, String> operations = redisTemplate.boundHashOps(SECKILL_SESSION_PRO_KEY);
            for (SeckillProRelation relation : session.getSeckillProRelations()) {
                String token = UUID.randomUUID().toString().replace("-","");
                String keyName = session.getId() + "_" + relation.getPid();
                if (!operations.hasKey(keyName)) {
                    R r = wareFeignService.findOneById(relation.getPid().toString());
                    Material material = JSON.parseObject(JSON.toJSONString(r.getData().get("material")), Material.class);
                    SeckillProductInfoTo productInfoTo = new SeckillProductInfoTo();
                    productInfoTo.setMaterial(material);
                    productInfoTo.setSeckillCount(relation.getSeckillCount());
                    productInfoTo.setSeckillPrice(relation.getSeckillPrice());
                    productInfoTo.setSeckillLimit(relation.getSeckillLimit());
                    productInfoTo.setStartTime(session.getStartTime());
                    productInfoTo.setEndTime(session.getEndTime());
                    productInfoTo.setToken(token);
                    long startEnd = session.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    long now =System.currentTimeMillis();
                    long ttl = startEnd - now;
                    operations.put(keyName,JSON.toJSONString(productInfoTo));
                    operations.expire(ttl,TimeUnit.MILLISECONDS);
                    // 设置分布式商品信号量，以商品的数量为信号量，以此判断扣减库存和秒杀是否成功
                    // 使用库存作为分布式Redisson信号量（限流）
                    RSemaphore semaphore = redissonClient.getSemaphore(SECKILL_PRO_SEMAPHORE + token);
                    semaphore.trySetPermits(relation.getSeckillCount());
                    semaphore.expire(ttl,TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    // 2、上架描述场次缓存与相应的商品id
    private void uploadSessionInfos(List<SeckillSessionTo> sessions) {
        if (sessions != null && sessions.size() > 0) {
            for (SeckillSessionTo session : sessions) {
                long startTime = session.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long startEnd = session.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                String key = SECKILL_SESSION_KEY + "_" + session.getId()+ "_" + startTime + "_" + startEnd;
                // 只有当前key不存在时才缓存信息
                if (!redisTemplate.hasKey(key)) {
                    long now =System.currentTimeMillis();
                    long ttl = startEnd - now;
                    redisTemplate.opsForValue().set(key,session.getPids().toString(),ttl, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    // 1、扫描最近3天内的秒杀场次
    private List<SeckillSessionTo> findSessionsLatest3Days() {
        List<SeckillSessionTo> seckillSessionTos = new ArrayList<>();
        List<SeckillSession> sessions = this.list(new QueryWrapper<SeckillSession>().between("start_time", startTime(), endTime()));
        for (SeckillSession session : sessions) {
            List<SeckillProRelation> list = seckillProRelationService.list(new QueryWrapper<SeckillProRelation>().eq("promotion_session_id", session.getId()));
            List<Long> pids = list.stream().map(item -> {
                return item.getPid();
            }).collect(Collectors.toList());
            SeckillSessionTo seckillSessionTo = new SeckillSessionTo();
            BeanUtils.copyProperties(session,seckillSessionTo);
            seckillSessionTo.setPids(pids);
            seckillSessionTo.setSeckillProRelations(list);
            seckillSessionTos.add(seckillSessionTo);
        }
        return seckillSessionTos;
    }

    private String endTime() {
        LocalDate now = LocalDate.now();
        // 当前时间加上2天
        LocalDate plus = now.plusDays(2);
        LocalDateTime start = LocalDateTime.of(plus, LocalTime.MAX);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String startTime() {
        LocalDate now = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(now, LocalTime.MIN);
        return start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
