package com.xinyu.idol;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@Slf4j

public class ResourceApplication {
    
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ResourceApplication.class, args);
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        //String[] beanNames &#61; ctx.getBeanNamesForAnnotation(RestController.class);//所有添加该注解的bean
//        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
//        int i = 0;
//        for (String str : beanNames) {
//            log.info("beanName:{}", ++i+","+ str);
//        }
    }

}
