package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.custom.domain.WxPush;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @GetMapping("/")
    public boolean wxCheckAuth() {
        return true;
    }
//
//    @PostMapping("/push")
//    public void msgPush(@RequestBody WxPush pushMsg) {
//
//    }

}
