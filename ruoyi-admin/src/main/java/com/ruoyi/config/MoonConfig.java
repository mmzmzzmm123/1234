package com.ruoyi.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ruoyi.**","com.moon.**"})
@MapperScan(basePackages = {"com.ruoyi.**.mapper","com.moon.**.mapper"})
public class MoonConfig{

}
