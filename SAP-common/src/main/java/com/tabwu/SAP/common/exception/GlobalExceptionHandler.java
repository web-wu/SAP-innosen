package com.tabwu.SAP.common.exception;

import com.tabwu.SAP.common.entity.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/11 15:16
 * @Description:  全局异常统一处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        if (e instanceof AccessDeniedException) {
            log.error("用户角色权限不足，请切换用户，{}",e.getMessage());
            return R.error().message("用户角色权限不足，请切换用户");
        }
        log.error("服务发生未知异常，{}",e.getMessage());
        return R.error().message("服务发生未知异常");
    }


    @ExceptionHandler(CostomException.class)
    public R exceptionHandler(CostomException e) {
        log.error(e.getMessage());
        return R.error().message(e.getMessage());
    }

    /*@ExceptionHandler(RuntimeException.class)
    public R exceptionHandler(RuntimeException e) {
        if (e instanceof AccessDeniedException) {
            log.error("用户角色权限不足，请切换用户，{}",e.getMessage());
            return R.error().message("用户角色权限不足，请切换用户");
        }
        return R.error();
    }*/
}
