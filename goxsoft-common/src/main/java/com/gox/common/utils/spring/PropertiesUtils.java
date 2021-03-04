package com.gox.common.utils.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesUtils {
    public static String profile;
    @Value("${gox.profile}")
    public void setProfile(String profile){
        PropertiesUtils.profile = profile;
    }
}
