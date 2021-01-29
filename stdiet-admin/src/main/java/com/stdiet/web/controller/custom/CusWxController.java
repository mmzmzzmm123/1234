package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.custom.service.ISysWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @Autowired
    public ISysWxService sysWxService;

    @GetMapping("/checkSign")
    public boolean wxCheckAuth(@PathVariable String signature, @PathVariable String timestamp, @PathVariable String nonce) {
        return sysWxService.wxCheckAuth(signature, timestamp, nonce);
    }

}
