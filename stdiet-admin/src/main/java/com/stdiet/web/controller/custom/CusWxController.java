package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.custom.service.ISysWxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @Autowired
    public ISysWxService sysWxService;

    @GetMapping("/checkSign")
    public boolean wxCheckAuth(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce) {
        return sysWxService.wxCheckAuth(signature, timestamp, nonce);
    }

}
