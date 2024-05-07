package com.ruoyi.system.category;


import com.alibaba.fastjson2.JSON;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.category.mapper.TCategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RuoYiApplication.class)
public class TCategoryMapperTest {

    @Resource
    private TCategoryMapper categoryMapper;


    @Test
    public void queryTest(){
        System.out.println(JSON.toJSONString(categoryMapper.selectTCategoryListByProductId(1L)));
    }
}
