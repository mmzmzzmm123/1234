package com.ruoyi.web.core.config;

import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2的接口配置
 *
 * @author ruoyi
 */
@Configuration
public class SwaggerConfig
{
    /** 系统基础配置 */
    @Autowired
    private RuoYiConfig ruoyiConfig;
    /**
     * 创建API
     */


    /**
     * 添加摘要信息
     */
    @Bean
    public OpenAPI apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new OpenAPI()
                .info(new Info().title("标题：若依管理系统_接口文档")
                        .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                        .version("版本号:" + ruoyiConfig.getVersion())
                        .contact(new Contact().name(ruoyiConfig.getName())));

    }
}
