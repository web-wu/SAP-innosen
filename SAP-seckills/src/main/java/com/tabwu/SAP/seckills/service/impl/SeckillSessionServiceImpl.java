package com.tabwu.SAP.seckills.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tabwu.SAP.common.utils.PageUtil;
import com.tabwu.SAP.seckills.entity.SeckillProRelation;
import com.tabwu.SAP.seckills.entity.SeckillSession;
import com.tabwu.SAP.seckills.entity.to.SeckillSessionTo;
import com.tabwu.SAP.seckills.entity.vo.SeckillSessionVo;
import com.tabwu.SAP.seckills.mapper.SeckillSessionMapper;
import com.tabwu.SAP.seckills.service.ISeckillProRelationService;
import com.tabwu.SAP.seckills.service.ISeckillSessionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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
    private RedisTemplate<String,Object> redisTemplate;

    private final String SECKILL_SESSION_KEY = "seckill:session";

    private final String SECKILL_SESSION_PRO_KEY = "seckill:session:pro";

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
        // 2、上架秒杀场次缓存与相应的商品id
        uploadSessionInfos(seckillSessionTos);
        // 3、上架秒杀场次关联的商品详情信息、随机码、活动时间
        uploadSessionRaletionProInfos(seckillSessionTos);
    }

    // 3、上架秒杀场次关联的商品详情信息、随机码、活动时间
    private void uploadSessionRaletionProInfos(List<SeckillSessionTo> sessions) {
        for (SeckillSessionTo session : sessions) {
            // TODO
            redisTemplate.opsForHash().getOperations()
            for (Long pid : session.getPids()) {
                String token = UUID.randomUUID().toString().replace("-","");
                String key = session.getId() + "_" + pid;
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
                    redisTemplate.opsForList().leftPush(key,session.getPids());
                }
            }
        }
    }

    // 1、扫描最近3天内的秒杀场次
    private List<SeckillSessionTo> findSessionsLatest3Days() {
        List<SeckillSessionTo> seckillSessionTos = new ArrayList<>();
        List<SeckillSession> sessions = this.list(new QueryWrapper<SeckillSession>().between("start_time", startTime(), endTime()));
        for (SeckillSession session : sessions) {
            List<SeckillProRelation> list = seckillProRelationService.list(new QueryWrapper<SeckillProRelation>().eq("promotion_session_id", session.getId()).select("pid"));
            List<Long> pids = list.stream().map(item -> {
                return item.getPid();
            }).collect(Collectors.toList());
            SeckillSessionTo seckillSessionTo = new SeckillSessionTo();
            BeanUtils.copyProperties(session,seckillSessionTo);
            seckillSessionTo.setPids(pids);
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
