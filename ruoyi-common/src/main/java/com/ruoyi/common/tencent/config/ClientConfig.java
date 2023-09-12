package com.ruoyi.common.tencent.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.ruoyi.common.tencent.properties.OssProperties;
import com.ruoyi.common.tencent.properties.TencentProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author LAM
 * @date 2022/5/12 11:33
 */
@Configuration
@RequiredArgsConstructor
public class ClientConfig {

    private final TencentProperties tencentProperties;
    private final OssProperties ossProperties;

    /**
     * 构建cosClient
     *
     * @return cos对象
     * */
    @Bean
    @Lazy
    public COSClient cosClient(){
        COSCredentials cred = new BasicCOSCredentials(tencentProperties.getSecretId(),tencentProperties.getSecretKey());
        com.qcloud.cos.ClientConfig cosClient = new com.qcloud.cos.ClientConfig(new Region(ossProperties.getRegion()));
        return new COSClient(cred,cosClient);
    }
}
