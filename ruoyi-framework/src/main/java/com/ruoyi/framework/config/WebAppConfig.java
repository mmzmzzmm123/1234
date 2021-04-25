package com.ruoyi.framework.config;

import com.ruoyi.common.utils.ListParamHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebAppConfig extends WebMvcConfigurationSupport{
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(gInterceptor()).addPathPatterns("/**").excludePathPatterns("/sys/checkIsRemoteLogin");
        registry.addInterceptor(localInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/reg","/user/forgetPassword");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");//
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ListParamHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    @Bean
    CoreInterceptor localInterceptor() {
        return new CoreInterceptor();
    }

    @Bean
    GInterceptor gInterceptor() {
        return new GInterceptor();
    }



}