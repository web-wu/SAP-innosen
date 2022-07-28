package com.tabwu.SAP.sale.aspact;

import com.tabwu.SAP.common.constant.DbConstant;
import com.tabwu.SAP.sale.annotation.DB;
import com.tabwu.SAP.sale.config.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/28 16:43
 * @DESCRIPTION:
 */
@Aspect
@Component
public class DynamicDbAspect {

    @Pointcut("@annotation(com.tabwu.SAP.sale.annotation.DB)")
    public void aspect() {}

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DB annotation = method.getDeclaredAnnotation(DB.class);

        if (annotation == null) {
            //若方法上没有wu注解，则默认使用主库master
            DynamicDataSource.setContextHolder(DbConstant.MASTER);
        } else {
            //否则使用注解指定的数据源类型
            DynamicDataSource.setContextHolder(annotation.value());
        }

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            //方法执行完后恢复为默认数据源类型,同时删除threadlocal的value值，防止内存泄漏
            DynamicDataSource.removeContextHolder();
        }
        return result;
    }
}
