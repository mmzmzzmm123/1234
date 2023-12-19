package com.hxzh.front.security.tokenservice.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
@Configuration
@ConfigurationProperties(prefix = "token.task")
@Data
@Slf4j
class TaskTokenServiceTest {
    @InjectMocks
    TaskTokenService taskTokenService;
    @Mock
    HttpServletRequest request;

    @Value("${token.header}")
    String header;
    String secret;
    Integer expireTime;
    String prefix;
    String schema;
    //及时更新
    String token="Bearer,tasktoken,eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiZXhwIjoxNzA0OTA3MzU2fQ.9_lod2-2i9teaDSeVSRuCqF4MvmEDospAkhYCYOyhthu3s0slfYiMoQ0KyoiVfyF50KYZu6QjOygUD5K3lQu5A";
    @BeforeEach
    void Init(){
        taskTokenService.setHeader(header);
        taskTokenService.setSchema(schema);
        taskTokenService.setPrefix(prefix);
        taskTokenService.setSecret(secret);
        taskTokenService.setExpireTime(expireTime);
    }

    @Test
    void testGetAuthentication() {
        Mockito.when(request.getHeader(header)).thenReturn(token);
        Authentication actualAuthentication = taskTokenService.getAuthentication(request);
        log.info("actualAuthentication:{}",actualAuthentication);
        Assertions.assertNotNull(actualAuthentication);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme