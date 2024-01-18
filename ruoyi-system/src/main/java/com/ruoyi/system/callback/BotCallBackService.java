package com.ruoyi.system.callback;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.enums.BotEventEnum;
import com.ruoyi.system.callback.dto.bot.EventCallBackDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 朱少波
 * @date 2024/1/18/018 9:30
 */
@Slf4j
@Service
@AllArgsConstructor
public class BotCallBackService implements BeanPostProcessor {

    private final Map<BotEventEnum, Invoker> typeMapped = new ConcurrentHashMap<>();

    static class Invoker {
        private Object bean;
        private Method method;
        private Class<?> paramClass;

        public Invoker(Object bean, Method method, Class<?> paramClass) {
            super();
            this.bean = bean;
            this.method = method;
            this.paramClass = paramClass;
        }

        public void invoke(JSONObject jsonObject)
                throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (jsonObject == null) {
                try {
                    method.invoke(bean, new Object[]{});
                } catch (Exception e) {
                    method.invoke(bean, new Object[]{null});
                }
                return;
            }
            method.invoke(bean, new Object[]{jsonObject.toJavaObject(paramClass)});
        }

        public void invokeArray(JSONArray jsonArray)
                throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (jsonArray == null) {
                method.invoke(bean, new Object[]{null});
                return;
            }
            method.invoke(bean, new Object[]{jsonArray.toJavaList(paramClass)});
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(BotEvent.class)) {
                continue;
            }
            BotEvent botEvent = method.getAnnotation(BotEvent.class);
            typeMapped.putIfAbsent(botEvent.value(), new Invoker(bean, method, botEvent.parameterClass()));
            method.setAccessible(true);
        }
        return bean;
    }


    /**
     * 回调分发处理
     */
    public void callback(String msg) {

        EventCallBackDTO dto;
        try {
            dto = JSON.parseObject(msg, EventCallBackDTO.class);
            if (dto.getEventType() == null) {
                log.info("未配置枚举类型={}", msg);
                return;
            }
        } catch (Exception e) {
            log.info("未配置枚举类型={}", msg);
            return;
        }

        // 找到对应的处理器
        Invoker invoker = typeMapped.get(dto.getEventType());
        if (invoker == null) {
            log.info("未找到对应的type处理器 {}", dto);
            return;
        }
        log.info("收到BOT监控回调={}", msg);

        try {
            deal(invoker, dto.getData());
        } catch (InvocationTargetException e) {
            log.error("BOT回调处理异常 {}", dto, e.getTargetException());
        } catch (Exception e) {
            log.error("BOT回调处理异常 {}", dto, e);
        }
        return;
    }

    private void deal(Invoker invoker, Object data)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (data == null) {
            invoker.invoke(null);
            return;
        }
        if (data instanceof JSONObject) {
            JSONObject temp = (JSONObject) data;
            if (temp.isEmpty()) {
                invoker.invoke(null);
                return;
            }
            invoker.invoke(temp);
        } else if (data instanceof JSONArray) {
            invoker.invokeArray((JSONArray) data);
        } else {
            log.error("不知道的data类型 {} {}", data, data.getClass().getName());
        }
    }
}
