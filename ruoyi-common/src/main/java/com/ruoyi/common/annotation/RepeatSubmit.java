package com.ruoyi.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解防止表单重复提交
 * 
 * @author ruoyi
 *
 * @Inherited
 * 1.类继承关系中@Inherited的作用：类继承关系中，子类会继承父类使用的注解中被@Inherited修饰的注解
 * 2.接口继承关系中@Inherited的作用：接口继承关系中，子接口不会继承父接口中的任何注解，不管父接口中使用的注解有没有被@Inherited修饰
 * 3.类实现接口关系中@Inherited的作用：类实现接口时不会继承任何接口中定义的注解
 * @Documented
 * 标注生成javadoc的时候是否会被记录。
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepeatSubmit
{

}
