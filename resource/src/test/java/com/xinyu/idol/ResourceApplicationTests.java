package com.xinyu.idol;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xinyu.idol.framework.web.service.SysLoginService;
import com.xinyu.idol.framework.web.service.TokenService;
import com.xinyu.idol.manager.ContentManager;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.pojo.entity.vo.AddContentVo;
import net.minidev.json.JSONUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Types;
import java.util.Collections;
import java.util.UUID;

@SpringBootTest
class ResourceApplicationTests {

//    @Test
//    void mpGenerate() {
//        FastAutoGenerator.create("jdbc:mysql://localhost:3306/ruoyi-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8", "root", "123456")
//                .globalConfig(builder -> {
//                    builder.author("yu.li") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
//                            .outputDir("E:\\idea_project\\RuoYi-Vue\\resource\\src\\main\\java"); // 指定输出目录
//                })
//                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
//                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
//                    if (typeCode == Types.SMALLINT) {
//                        // 自定义类型转换
//                        return DbColumnType.INTEGER;
//                    }
//                    return typeRegistry.getColumnType(metaInfo);
//
//                }))
//                .packageConfig(builder -> {
//                    builder.parent("com.xinyu.idol") // 设置父包名
//                            .moduleName("") // 设置父包模块名
//                            .entity("pojo.entity")//设置entity
//                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\idea_project\\RuoYi-Vue\\resource\\src\\main\\resources\\mappers")); // 设置mapperXml生成路径
//                })
//                .strategyConfig(builder -> {
//                    builder.addInclude("t_content","t_content_oper_log","t_pak_path_id_map")// 设置需要生成的表名
//                            .addTablePrefix("t_", "c_")// 设置过滤表前缀
//                     .entityBuilder().formatFileName("%sEntity");//设置entity规则
//
//                })
//
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
//
//    }

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private TokenService tokenService;
    @Test
    void generateToken(){
        //tokenService.createToken()
        //loginService.login()

    }

    @Test
    void jsonToBean(){
        AddContentVo addContentVo= AddContentVo.builder()
                .classification1("c1")
                .classification2("c2")
                .classification3("c3")
                .classification4("c4")
                .description("desc")
                .displayName("disp").fileName("fileN").currentUploadTime("123").pakOsskey("pakOss").jsonOsskey("Json")
        .build();

        addContentVo.setPath(UUID.randomUUID().toString());
        //String jsonStr = JSONUtils.toJSONString(addContentVo);
        String s = JSON.toJSONString(addContentVo);

        System.out.println("json->"+s);

    }

    @Autowired
    ContentManager contentManager;
    @Test
    void testmpnull(){
        ContentEntity contentEntityInDb = contentManager.selectByIdentifier("f635461852488667a6649420969fb949");

        if(ObjectUtils.isNotEmpty(contentEntityInDb)){
            return;
        }


    }
}
