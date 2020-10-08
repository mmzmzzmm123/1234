package com.ruoyi.web.test.controller;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * @auther: Wang
 * @Date: 2020/08/08 19:10
 * 功能描述:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = BaseSpringBootTest.class)
@SpringBootTest
@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath*:*.xml"})
public class BaseSpringBootTest {
    protected  static final Logger logger = LoggerFactory.getLogger(BaseSpringBootTest.class);
    @Before
    public void init() {
        logger.info("开始测试...");
    }

    @After
    public void after() {
       logger.info("测试结束...");
    }




}
