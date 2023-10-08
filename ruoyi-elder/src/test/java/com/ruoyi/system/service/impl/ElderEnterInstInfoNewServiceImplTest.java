package com.ruoyi.system.service.impl;

import com.ruoyi.RuoYiApplication;
import com.ruoyi.system.service.ElderEnterInstInfoNewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RuoYiApplication.class)
class ElderEnterInstInfoNewServiceImplTest {
    @Autowired
    private ElderEnterInstInfoNewService elderEnterInstInfoNewService;
    @Test
    public void selectById() {
        elderEnterInstInfoNewService.getById("0131fe52d8ccc689569ecf466d0b35bd");
    }
}