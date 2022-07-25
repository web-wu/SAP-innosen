package com.tabwu.SAP.common.annotation;

import java.lang.annotation.*;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/6/23 16:26
 * @DESCRIPTION:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Log {
    String module() default "";
    String method() default "";
    String operateType() default "";
}
