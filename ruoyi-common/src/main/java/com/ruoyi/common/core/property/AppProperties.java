package com.ruoyi.common.core.property;

import com.ruoyi.common.enums.ConfKey;
import com.ruoyi.common.utils.ApplicationContextHolder;
import org.springframework.core.env.Environment;

/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 16:12 xuc5566@gmail.com
 */
public class AppProperties {
    private static class SingletonHolder {
        private static Environment environment =
                ApplicationContextHolder.getApplicationContext().getEnvironment();
    }

    public static String getProperty(ConfKey confKey) {
        return SingletonHolder.environment.getProperty(confKey.getKey(), confKey.getDefaultValue());
    }

    private AppProperties() {
    }
}
