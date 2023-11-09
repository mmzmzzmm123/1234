package com.xinyu.idol;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson2.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyuncs.auth.EnvironmentVariableCredentialsProvider;
import com.xinyu.idol.common.core.redis.RedisCache;
import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.framework.web.service.SysLoginService;
import com.xinyu.idol.framework.web.service.TokenService;
import com.xinyu.idol.manager.ContentManager;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.pojo.vo.AddContentVo;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
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

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    RedisCache redisCache;


    @Test
    void testRedisQueue(){
        String queueKey="Message:Queue:ArtUpload";
        com.xinyu.idol.domain.TArtResourceOrigin tArtResourceOrigin = redisCache.rPop(queueKey);
        System.out.println(tArtResourceOrigin.toString());


    }

    @Test
    void jsonToBean(){

        AddContentVo addContentVo= AddContentVo.builder()
                .classification1("c1")
                .classification2("c2")
                .classification3("c3")
                .classification4("c4")
                .description("desc")
                .iconOsskey("icon_oss")
                .pakFileSize(100)
                .specialTag("tag")
                .pakSha1("sha1")
                .ownerid("213").jsonFileSize(101)

                .type("type1")
                .path("path/aaa/bb")
                .osskeyList("{\"android\" :\"aaa\",\"ios\":\"ccc\"}")

                .displayName("disp").fileName("fileN").currentUploadTime("123").pakOsskey("pakOss").jsonOsskey("Json")
        .build();


        addContentVo.setPath(UUID.randomUUID().toString());
       redisCache.lPush("testKey33",addContentVo);

        System.out.println(JSON.toJSONString(addContentVo));




    }

    @Autowired
    ContentManager contentManager;
//    @Test
//    void testmpnull(){
//        String filePath= "D:\\localpath\\examplefile.txt";
//
//        String bucketName = "examplebucket";
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "exampledir/exampleobject.txt";
//
//        // 创建OSSClient实例。
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
//
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
//    }

//    @Test
//    void testAliOss(){
//        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
//        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
//        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "examplebucket";
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "exampledir/exampleobject.txt";
//        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        String filePath= "D:\\localpath\\examplefile.txt";
//
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, );
//
//
//        try {
//            InputStream inputStream = new FileInputStream(filePath);
//            // 创建PutObjectRequest对象。
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
//            // 创建PutObject请求。
//            PutObjectResult result = ossClient.putObject(putObjectRequest);
//        } catch (OSSException oe) {
//            System.out.println("Caught an OSSException, which means your request made it to OSS, "
//                    + "but was rejected with an error response for some reason.");
//            System.out.println("Error Message:" + oe.getErrorMessage());
//            System.out.println("Error Code:" + oe.getErrorCode());
//            System.out.println("Request ID:" + oe.getRequestId());
//            System.out.println("Host ID:" + oe.getHostId());
//
//        } catch (ClientException ce) {
//            System.out.println("Caught an ClientException, which means the client encountered "
//                    + "a serious internal problem while trying to communicate with OSS, "
//                    + "such as not being able to access the network.");
//            System.out.println("Error Message:" + ce.getMessage());
//
//        } finally {
//            if (ossClient != null) {
//                ossClient.shutdown();
//            }
//        }
//    }
}
