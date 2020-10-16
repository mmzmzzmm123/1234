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
                .excludePathPatterns("/user/login", "/user/reg","/user/forgetPassword",
                        "/training/uploadData","/swagger-resources/**", "/webjars/**", "/v2/**",
                        "/swagger-ui.html/**","/pay/wx/notify","/sms/getValidateCode","/sms/getWxValidateCode",
                        "/sys/upgrade","/trainingpage/**","/static/**","/coachAdmin/**","/sys/getSysConfig",
                        "/activity/**","/actuator/**","/sys/getShareImage","/pay/v2/wx/notify",
                        "/user/wx/login","/user/wx/reg","/user/smsLogin", "/user/smsSecurity", "/user/smsReg","/sms/v4/getValidateCode",
                        "/v3/wxpays/contracts/pay/notify", "/v3/wxpays/contracts/refund/notify",
                        "/v3/wxpays/contracts/order/notify", "/v3/wxpays/contracts/notify","/v3/iapay/contracts/status/notify","/encryptDecrypt/getEncryptKey",
                        "/v4/user/email/code", "/v4/user/email/reg", "/v4/user/email/login", "/v4/user/email/forgetPassword",
                        "/v4/user/third/login","/v4/user/third/is_reg","/v4/user/third/reg","/payCallback/callback/**");
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