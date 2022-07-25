package com.tabwu.SAP.production.aspact;

import com.tabwu.SAP.common.annotation.Log;
import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.common.utils.JwtUtils;
import com.tabwu.SAP.production.feign.LogFeignService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/25 16:10
 * @DESCRIPTION:
 */
@Aspect
@Component
public class LogAspact {

    @Autowired
    private LogFeignService logFeignService;

    @Pointcut("@annotation(com.tabwu.SAP.common.annotation.Log)")
    public void aspect() {

    }

    @Around("aspect()")
    //joinPoint 目标类中的切点方法
    public void around(ProceedingJoinPoint joinPoint) {

        long startime = System.currentTimeMillis();

        //获取请求uri
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteHost = request.getRemoteHost();
        //用户信息
        LoginUser loginUser = JwtUtils.getLoginUserByToken(request);

        //获取注解参数和 切点方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log annotation = method.getAnnotation(Log.class);

        //获取入参

        //传入的 参数值 数组
        Object[] args = joinPoint.getArgs();
        //方法的 参数名 数组
        String[] argsNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        HashMap<String, Object> paramters = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            paramters.put(argsNames[i],args[i]);
        }

        //获取出参
        Object result = null;
        try {
            //执行切点方法
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        Logs logs = new Logs();
        if (annotation != null) {
            logs.setMethod(annotation.method());
            logs.setModule(annotation.module());
            logs.setOperateType(annotation.operateType());
        }
        //获取耗时
        long endtime = System.currentTimeMillis();
        String ttl = (endtime - startime) + "ms";

        logs.setUsername(loginUser.getUsername());
        logs.setInputParams(paramters.toString());
        logs.setOutputParams(result.toString());
        logs.setClientIp(remoteHost);
        logs.setContent(ttl);

        logFeignService.addLog(logs);
    }

}
