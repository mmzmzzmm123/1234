package com.ruoyi.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 * 
 * @author ruoyi
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InterfaceLog
{
    /**
     * 模块 
     */
    public String title() default "";

    /**
     * 接口请求地址
     */
    public String url() default "";

    /**
     * 请求参数
     */
    public String parameter() default "";

    /**
     * 政务信息网、政务外网互联网接入区、政务外网公用网络区、政务外网专业网络区
     */
    public String requestNetWork() default "";

    /**
     * 查询事由
     */
    public String requestReason() default "";

    /**
     * 操作者姓名
     */
    public String operaterName() default "";

    /**
     * 操作者身份证
     */
    public String operaterIdCard() default "";

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;
}
