package com.stdiet.custom.service;

public interface ISysRedisService {
    void set(String key, String value);

    String get(String key);

    Boolean expire(String key, long expire);

    void remove(String key);

    Long increment(String key, long delta);
}
