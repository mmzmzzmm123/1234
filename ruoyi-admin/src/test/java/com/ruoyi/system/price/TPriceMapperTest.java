package com.ruoyi.system.price;


import com.alibaba.fastjson2.JSON;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.app.product.ao.UserProductAO;
import com.ruoyi.system.price.mapper.TPriceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RuoYiApplication.class)
public class TPriceMapperTest {
    @Resource
    private TPriceMapper priceMapper;

    @Test
    public void queryTest(){
        System.out.println(priceMapper.selectTPriceById(3L));
    }

    @Test
    public void queryLastTpriceTest(){
        System.out.println(priceMapper.queryLastTprice(1L,null));
    }
}
