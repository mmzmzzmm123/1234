package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.custom.service.ISysWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @Autowired
    public ISysWxService sysWxService;

    @GetMapping("/checkSign")
    public String wxCheckAuth(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        return sysWxService.wxCheckAuth(signature, timestamp, nonce, echostr);
    }

}
