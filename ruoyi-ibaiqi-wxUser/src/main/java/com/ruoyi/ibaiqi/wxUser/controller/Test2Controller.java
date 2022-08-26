package com.ruoyi.ibaiqi.wxUser.controller;

import com.ruoyi.common.annotation.Anonymous;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhangxuDev
 * @Date 2022/8/17 10:52
 * @Version 1.0.0
 */

@RestController
@RequestMapping("/api/v1/wxUser")
public class Test2Controller {
    /**
     * 查看此接口是否可匿名访问
     * @return
     */
    @Anonymous
    @GetMapping("/list")
    public String testPrint() {
        return "返回打印的值aa";
    }
}
