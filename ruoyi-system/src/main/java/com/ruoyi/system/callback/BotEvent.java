package com.ruoyi.system.callback;

import com.ruoyi.common.enums.BotEventEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BotEvent {

    BotEventEnum value();

    Class<?> parameterClass();

}