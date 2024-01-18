package com.ruoyi.system.callback;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.callback.dto.CalledDTOThreadLocal;
import com.ruoyi.system.openapi.OpenApiRequestCache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class CallBackService implements BeanPostProcessor {

    private final Map<Long, Invoker> typeMapped = new ConcurrentHashMap<>();

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
            if (!method.isAnnotationPresent(Type.class)) {
                continue;
            }
            Type type = method.getAnnotation(Type.class);
            typeMapped.putIfAbsent(type.value(), new Invoker(bean, method, type.parameterClass()));
            method.setAccessible(true);
        }
        return bean;
    }


    /**
     * 回调分发处理
     *
     * @param resultParams
     */
    public void callback(String resultParams) {
        // 转成 实体
        CalledDTO root = JSON.parseObject(resultParams, CalledDTO.class);
        root.setRequestPara(OpenApiRequestCache.getAndRemove(root.getOptSerNo()));

        Long type = root.getType();
        if (type == null) {
            return;
        }
        // 找到对应的处理器
        Invoker invoker = typeMapped.get(type);
        if (invoker == null) {
            log.info("未找到对应的type处理器 {}", root);
            return;
        }
        try {
            // 设置 环境
            CalledDTOThreadLocal.set(root);
            deal(invoker, root);
        } catch (InvocationTargetException e) {
            log.error("回调处理异常 {}", root, e.getTargetException());
        } catch (Exception e) {
            log.error("回调处理异常 {}", root, e);
        } finally {
            CalledDTOThreadLocal.remove();
        }
        return;
    }

    private void deal(Invoker invoker, CalledDTO root)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object data = root.getData();
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
            log.error("不知道的data类型 {} {}", root, data.getClass().getName());
        }
    }

}
