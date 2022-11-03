package com.tabwu.SAP.attendance.service.impl;

import com.tabwu.SAP.attendance.service.SignIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/10/28 15:27
 * @DESCRIPTION:
 */
@Service
public class SignInImpl implements SignIn {

    private static final String KEY_PREFIX = "sgin:";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public int signIn(String id) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 获取当月的每一天，几号
        int dayOfMonth = date.getDayOfMonth();
        // 当日的结果为true时，表示已经签过到了，不能重复签到
        if (redisTemplate.opsForValue().getBit(key, dayOfMonth)) {
            return 0;
        }

        redisTemplate.opsForValue().setBit(key, dayOfMonth, true);
        return 1;
    }

    @Override
    public int getSignIn(String id) {

        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 获取当月的每一天，几号
        int dayOfMonth = date.getDayOfMonth();
        // 当日的结果为true时，表示已经签过到了
        if (!redisTemplate.opsForValue().getBit(key, dayOfMonth)) {
            return 0;
        }

        return 1;
    }

    @Override
    public Boolean checkStatusByDayOfMouth(String id, int dayOfMouth) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));

        // 当日的结果为true时，表示已经签过到了
        return redisTemplate.opsForValue().getBit(key, dayOfMouth);
    }

    @Override
    public int resignIn(String id, int dayOfMouth) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));

        int currentDay = date.getDayOfMonth();
        // 指定补签日期不能大于当前日期
        if (dayOfMouth > currentDay) {
            return 0;
        }

        // 当日的结果为true时，表示已经签过到了，不能重复签到,不进行补签操作
        if (redisTemplate.opsForValue().getBit(key, dayOfMouth)) {
            return 0;
        }
        redisTemplate.opsForValue().setBit(key, dayOfMouth, true);
        return 1;
    }

    @Override
    public Integer countSignIn(String id) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));

        Long count = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
        assert count != null;
        return count.intValue();
    }

    @Override
    public int signCount(String id) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        // 获取当月的每一天，几号
        int dayOfMonth = date.getDayOfMonth();
        // 获取从 1号 到 dayOfMonth 的签到结果
        List<Long> longs = redisTemplate.opsForValue().bitField(key,
                BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(1));
        if (longs == null || longs.isEmpty()) {
            return 0;
        }
        Long num = longs.get(0);
        if (num == null || num == 0) {
            return 0;
        }

        int count = 0;

        while (true) {
            // 让这个数字与 1 做与运算，得到数字的最后一位 bit 位  判断这个 bit 位是否为0
            // num & 1 做与运算，其中 1 的左边以 0 补齐
            if ((num & 1) == 0) {
                break;
            } else {
                count++;
            }
            // 把数字右移一位，抛弃最后一个 bit 位，继续下一个 bit 位
            // >> :右移 最高位是0，左边补齐0;最高为是1，左边补齐1
            // << :左移 左边最高位丢弃，右边补齐0
            // >>>:无符号右移 无论最高位是0还是1，左边补齐0
            // num >>>= 1 ————> num = num >>> 1
            num >>>= 1;
        }

        return count;
    }

    @Override
    public int getFirstSign(String id) {
        // 获取当前时间，格式化时间
        LocalDate date = LocalDate.now();
        String key = KEY_PREFIX + id + date.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        int day = 1;
        while (day <= 31) {
            if (redisTemplate.opsForValue().getBit(key,day)) {
                break;
            }
            day++;
        }
        return day;
    }
}
