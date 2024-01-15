package com.ruoyi;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class Generator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://bigroc.cn:32770/engineering_manager?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
        String username = "hehao";
        String password = "hehao123";
        String author = "HH";
        String outputDir = "D:\\java-product\\gcgj\\engineering-manager\\src\\main\\java";
        String basePackage = "com.ruoyi";
        String mapperLocation = "D:\\java-product\\gcgj\\engineering-manager\\src\\main\\resources\\mapper\\";
        String tableName = "source_calculation_direct_cost";
        String tablePrefix = "";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author)// 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(basePackage) // 设置父包名
                           // .moduleName("com.engineeringmanager") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .addTablePrefix(tablePrefix); // 设置过滤表前缀

                })
                .templateEngine(new VelocityTemplateEngine()) // 使用默认的Velocity引擎模版
                .execute();

    }
}
