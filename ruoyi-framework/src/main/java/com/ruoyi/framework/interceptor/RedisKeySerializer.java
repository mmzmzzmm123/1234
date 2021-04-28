package com.ruoyi.framework.interceptor;

import com.ruoyi.common.config.RuoYiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

@Component
public class RedisKeySerializer implements RedisSerializer<String> {

    @Autowired
    private RuoYiConfig ruoyiConfig;

    private final Charset charset;

    public RedisKeySerializer() {
        this(Charset.forName("UTF8"));
    }

    public RedisKeySerializer(Charset charset) {
        Assert.notNull(charset, "字符集不允许为NULL");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(String string) throws SerializationException {
        //通过项目名称ruoyi.name来定义Redis前缀，用于区分项目缓存
        if(StringUtils.isEmpty(string)){
            return null;
        }else{
            if(StringUtils.isEmpty(ruoyiConfig.getName())){
                return string.getBytes(charset);
            }
        }
        return new StringBuilder(ruoyiConfig.getName()).append(":").append(string).toString().getBytes(charset);
    }

    @Override
    public String deserialize(byte[] bytes) throws SerializationException {
        return (bytes == null ? null : new String(bytes, charset));
    }
}
