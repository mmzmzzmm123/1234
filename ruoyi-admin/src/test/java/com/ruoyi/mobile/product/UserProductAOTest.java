package com.ruoyi.mobile.product;


import com.alibaba.fastjson2.JSON;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.app.product.ao.UserProductAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RuoYiApplication.class)
public class UserProductAOTest {
    @Resource
    private UserProductAO userProductAO;

    @Test
    public void querySubscribeProductTest(){
        System.out.println(JSON.toJSONString(userProductAO.querySubscribeProduct(1L)));
    }

}
