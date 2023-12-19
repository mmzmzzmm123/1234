package com.hxzh.front.config;

import com.hxzh.front.security.provider.TaskAuthenticationProvider;
import com.hxzh.front.security.tokenservice.impl.TaskTokenService;
import com.hxzh.front.security.tokenservice.impl.UserTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SecurityBeanConfig {

    @Bean
    public UserTokenService userTokenService(){
        return new UserTokenService();
    }

    @Bean
    public TaskTokenService taskTokenService(){
        return new TaskTokenService();
    }
}