package com.tabwu.SAP.base.aspact;

import com.tabwu.SAP.base.service.ILogsService;
import com.tabwu.SAP.common.annotation.Log;
import com.tabwu.SAP.common.entity.LoginUser;
import com.tabwu.SAP.common.entity.Logs;
import com.tabwu.SAP.common.utils.JwtUtils;
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
    private ILogsService logsService;

    @Pointcut("@annotation(com.tabwu.SAP.common.annotation.Log)")
    public void aspect() {

    }

    @Around("aspect()")
    //joinPoint 目标类中的切点方法
    public void around(ProceedingJoinPoint joinPoint) {


        //获取请求ip
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String remoteHost = request.getRemoteAddr();
        //用户信息
        LoginUser loginUser = JwtUtils.getLoginUserByToken(request);

        //获取注解参数和 切点方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Log annotation = method.getAnnotation(Log.class);

        //获取入参

        //传入的 参数值 实参数组
        Object[] args = joinPoint.getArgs();
        //方法的 参数名 形参数组
        String[] argsNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        HashMap<String, Object> paramters = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            paramters.put(argsNames[i],args[i]);
        }

        long startime = System.currentTimeMillis();

        //获取出参
        Object result = null;
        try {
            //执行切点方法
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //获取耗时
        long endtime = System.currentTimeMillis();
        String ttl = (endtime - startime) + "ms";

        Logs logs = new Logs();
        if (annotation != null) {
            logs.setModule(annotation.module());
            logs.setMethod(annotation.method());
            logs.setOperateType(annotation.operateType());
        }

        if (loginUser != null) {
            logs.setUsername(loginUser.getUsername());
        }

        logs.setInputParams(paramters.toString());
        if(result != null) {
            logs.setOutputParams(result.toString());
        }
        logs.setClientIp(remoteHost);
        logs.setContent(ttl);

        logsService.save(logs);
    }

}
