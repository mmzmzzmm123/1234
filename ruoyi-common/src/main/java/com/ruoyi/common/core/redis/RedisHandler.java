package com.ruoyi.common.core.redis;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis 操作
 * 
 * redisTemplate.opsForValue(); //�����ַ��� <br>
 * redisTemplate.opsForHash(); //����hash<br>
 * redisTemplate.opsForList(); //����list<br>
 * redisTemplate.opsForSet(); //����set<br>
 * redisTemplate.opsForZSet(); //��������set<br>
 *
 */
@Slf4j
@Component
public class RedisHandler implements BeanFactoryAware {

    private static BeanFactory beanFactory;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * get RedisTemplate����
     * 
     * 
     * @return
     * @author HadLuo 2020-9-2 11:42:22
     */
    public static RedisTemplate getRedisTemplate() {
        return beanFactory.getBean(RedisHandler.class).redisTemplate ;
    }

    /**
     * title: 执行lua 脚本
     *
     * @param <T>
     * @param returnType
     *            lua脚本返回值类型
     * @param lua
     *            脚本字符串
     * @param keys
     *            所有的 key
     * @param args
     *            参数值
     * @return
     * @author HadLuo 2020-9-10 11:51:27
     */
//    public static <T> T execLua(Class<T> returnType, String lua, List<String> keys, String... args) {
//        Object[] argsA = args;
////        return getRedisTemplate().execute(new DefaultRedisScript<>(lua, returnType), keys, argsA);
//    }

    /**
     * set with expire time
     * 
     * 
     * @param key
     * @param value
     * @param time
     *            单位 s
     * @author HadLuo 2020-9-2 11:42:33
     */
    public static void setExpire(final String key, final String value, final long time) {
        getRedisTemplate().opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * string set
     * 
     * 
     * @param key
     * @param value
     * @author HadLuo 2020-9-2 11:43:11
     */
    public static void set(final String key, final Object value) {
        if (StringUtils.isEmpty(key) || value == null) {
            return;
        }
        if (value instanceof String) {
            getRedisTemplate().opsForValue().set(key, value);
        } else {
            getRedisTemplate().opsForValue().set(key, JSON.toJSONString(value));
        }
    }

    /**
     * string get
     * 
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:43:18
     * @param <T>
     */
    public static <T> T get(final String key, Class<T> clazz) {
        if (StringUtils.isEmpty(key) || clazz == null) {
            return null;
        }
        Object object = getRedisTemplate().opsForValue().get(key);
        if (object == null) {
            return null;
        }
        return JSON.parseObject((String)object, clazz);
    }

    /**
     * string set
     * 
     * 
     * @param key
     * @param value
     * @author HadLuo 2020-9-2 11:43:11
     */
    public static void set(final String key, final String value) {
        getRedisTemplate().opsForValue().set(key, value);
    }

    /**
     * string get
     * 
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:43:18
     */
    public static String get(final String key) {
        return (String)getRedisTemplate().opsForValue().get(key);
    }

    /**
     * del key
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:43:32
     */
    public static Boolean del(final String key) {
        return getRedisTemplate().delete(key);
    }

    public static Long ttl(final String key) {
        return getRedisTemplate().getExpire(key);
    }

    /**
     * title : 指定key的过期时间，单位s
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:43:32
     */
    public static Boolean expire(String key, int second) {
        return getRedisTemplate().expire(key, second, TimeUnit.SECONDS);
    }

    /**
     * title : 指定key在哪个日期过期
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:43:32
     */
    public static Boolean expireAt(String key, Date date) {
        return getRedisTemplate().expireAt(key, date);
    }



    /**
     * incr
     * 
     * @param key
     * @author HadLuo 2020-9-2 11:43:54
     */
    public static void incr(final String key) {
        getRedisTemplate().opsForValue().increment(key);
    }

    /**
     * incr
     * 
     * @param key
     * @param delta
     * @author HadLuo 2020-9-2 11:44:02
     */
    public static void incr(final String key, long delta) {
        getRedisTemplate().opsForValue().increment(key, delta);
    }

    /**
     * hset ����
     * 
     * @param key
     * @param hashKey
     * @param hashValue
     * @author HadLuo 2020-9-2 11:44:10
     */
    public static void hset(String key, String hashKey, String hashValue) {
        getRedisTemplate().opsForHash().put(key, hashKey, hashValue);
        log.info("redis hset >> k = " + key + " , hashKey = " + hashKey + " , hashValue = " + hashValue);
    }

    /**
     * hget����
     * 
     * @param key
     * @param hashKey
     * @return
     * @author HadLuo 2020-9-2 11:44:24
     */
    public static String hget(String key, String hashKey) {
        String value = (String)getRedisTemplate().opsForHash().get(key, hashKey);
        log.info( "redis hget >> k = " + key + " , hashKey = " + hashKey + " , return = " + value);
        return value;
    }

    /**
     * delete by key
     * 
     * @param key
     * @param hashKeys
     * @author HadLuo 2020-9-2 11:44:36
     */
    public static void hdel(String key, Object... hashKeys) {
        getRedisTemplate().opsForHash().delete(key, hashKeys);
    }

    /**
     * exists key
     * 
     * @param key
     * @return
     * @author HadLuo 2020-9-2 11:49:45
     */
    public static boolean exists(final String key) {
        return getRedisTemplate().hasKey(key);
    }

    /**
     * 
     * title: list 右边添加元素
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     */
    public static void rpush(String key, Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        if (value instanceof String) {
            getRedisTemplate().opsForList().rightPush(key, value);
        } else {
            getRedisTemplate().opsForList().rightPush(key, JSON.toJSONString(value));
        }
    }

    /**
     * 
     * title: list 左边添加元素
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     */
    public static void lpush(String key, Object value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return;
        }
        if (value instanceof String) {
            getRedisTemplate().opsForList().leftPush(key, value);
        } else {
            getRedisTemplate().opsForList().leftPush(key, JSON.toJSONString(value));
        }
    }

    /**
     * 
     * title: list 左边弹出元素
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static String lpop(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return (String)getRedisTemplate().opsForList().leftPop(key);
    }

    /**
     * 
     * title: list 左边弹出元素
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static String rpop(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return (String)getRedisTemplate().opsForList().rightPop(key);
    }

    /**
     * 
     * title: list 左边弹出元素
     *
     * @param key
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static <T> T lpop(String key, Class<T> clazz) {
        if (clazz == null || StringUtils.isEmpty(key)) {
            return null;
        }
        Object object = getRedisTemplate().opsForList().leftPop(key);
        if (object == null) {
            return null;
        }
        String json = (String)object;
        return JSON.parseObject(json, clazz);
    }

    /**
     * 
     * title: list 右边弹出元素
     *
     * @param key
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static <T> T rpop(String key, Class<T> clazz) {
        if (clazz == null || StringUtils.isEmpty(key)) {
            return null;
        }
        Object object = getRedisTemplate().opsForList().rightPop(key);
        if (object == null) {
            return null;
        }
        String json = (String)object;
        return JSON.parseObject(json, clazz);
    }

    /**
     * 
     * title: list 队列大小
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static Long llen(String key) {
        if (StringUtils.isEmpty(key)) {
            return 0L;
        }
        return getRedisTemplate().opsForList().size(key);
    }

    /**
     * 
     * title: list遍历
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> lrange(String key, int start, int end, Class<T> clazz) {
        if (StringUtils.isEmpty(key)) {
            Collections.emptyList();
        }
        List<Object> datas = getRedisTemplate().opsForList().range(key, start, end);
        if (CollectionUtils.isEmpty(datas)) {
            Collections.emptyList();
        }
        List<T> results = Lists.newArrayList();
        for (Object obj : datas) {
            if (obj instanceof String) {
                results.add(JSON.parseObject((String)obj, clazz));
            } else {
                results.add((T)obj);
            }
        }
        return results;
    }

    /**
     * 
     * title: list遍历
     *
     * @param key
     * @param value
     * @author HadLuo 2020-9-16 9:18:07
     * @param <T>
     */
    public static List<String> lrange(String key, int start, int end) {
        if (StringUtils.isEmpty(key)) {
            Collections.emptyList();
        }
        List<Object> datas = getRedisTemplate().opsForList().range(key, start, end);
        if (CollectionUtils.isEmpty(datas)) {
            Collections.emptyList();
        }
        List<String> results = Lists.newArrayList();
        for (Object obj : datas) {
            results.add((String)obj);
        }
        return results;
    }

    /**
     * 
     * title: zset zrange
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author HadLuo 2020-9-16 9:49:00
     */
    public static Set<String> zrange(String key, long start, long end) {
        Set<Object> sets = getRedisTemplate().opsForZSet().range(key, start, end);
        if (CollectionUtils.isEmpty(sets)) {
            return Collections.emptySet();
        }
        Set<String> ret = new HashSet<String>();
        for (Object obj : sets) {
            ret.add((String)obj);
        }
        return ret;
    }

    /**
     * 
     * title: zset zrange
     *
     * @param key
     * @param start
     * @param end
     * @return
     * @author HadLuo 2020-9-16 9:49:00
     * @param <T>
     */
    public static <T> Set<T> zrange(String key, long start, long end, Class<T> clazz) {
        Set<Object> sets = getRedisTemplate().opsForZSet().range(key, start, end);
        if (CollectionUtils.isEmpty(sets)) {
            return Collections.emptySet();
        }
        Set<T> ret = new HashSet<T>();
        for (Object obj : sets) {
            ret.add(JSON.parseObject((String)obj, clazz));
        }
        return ret;
    }

    /***
     * 
     * title: zadd
     *
     * @param key
     * @param value
     * @param score
     * @return
     * @author HadLuo 2020-11-18 18:51:29
     */
    public static boolean zadd(String key, String value, double score) {
        return getRedisTemplate().opsForZSet().add(key, value, score);
    }

    /***
     * 
     * title: zrem
     *
     * @param key
     * @param values
     * @return
     * @author HadLuo 2020-11-18 18:53:03
     */
    public static Long zrem(String key, String... values) {
        if (StringUtils.isEmpty(values)) {
            return 0L;
        }
        List<Object> ids = Lists.newArrayList();
        for (String id : values) {
            ids.add(id);
        }
        return getRedisTemplate().opsForZSet().remove(key, ids.toArray());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        RedisHandler.beanFactory = beanFactory;
    }
}
