package com.tabwu.SAP.sale.annotation;

import java.lang.annotation.*;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/28 16:39
 * @DESCRIPTION:
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface DB {
    String value() default "";
}
