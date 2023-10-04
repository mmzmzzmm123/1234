package com.ruoyi.common.core.redis;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyMapCache{

    public ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<String, Object>();

    /**
     * 放入值
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        concurrentHashMap.put(key, value);
    }

    /**
     * 取值
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return concurrentHashMap.get(key);
    }

    /**
     * 移除
     *
     * @param k
     */
    public void remove(String key) {
        concurrentHashMap.remove(key);
    }

}

