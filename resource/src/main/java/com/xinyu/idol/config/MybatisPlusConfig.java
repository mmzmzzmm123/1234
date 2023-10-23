package com.xinyu.idol.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * MP分页插件失效，可能是排除依赖导致，也可能是和pageHelper冲突导致，后续分页逻辑自己手写
 */


//@Configuration
//@MapperScan("com.xinyu.idol.mapper")
//public class MybatisPlusConfig {
//    /**
//     * 添加分页插件
//     */
//    @Bean
//    public MybatisPlusInterceptor mybatisPlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//
//    }
//
//
//}
