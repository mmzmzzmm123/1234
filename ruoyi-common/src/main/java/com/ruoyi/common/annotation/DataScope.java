package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限过滤注解
 * 
 * @author ruoyi
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope
{
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";
    /**
     * 部门字段名
     */
    public String deptColumnName() default "dept_id";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";
    /**
     * 用户字段名
     */
    public String userColumnName() default "user_id";
}
