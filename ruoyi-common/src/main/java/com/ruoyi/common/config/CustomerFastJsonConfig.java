package com.ruoyi.common.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.springframework.stereotype.Component;


/**
 * @author Jing.Zhang
 */
@Component
public class CustomerFastJsonConfig {


    public CustomerFastJsonConfig(FastJsonHttpMessageConverter converter) {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.FieldBased);
        converter.setFastJsonConfig(fastJsonConfig);
    }

}
