package com.ruoyi.web.controller.callback;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.system.callback.CallBackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CallbackController {

    private final CallBackService callBackService;

    @Anonymous
    @PostMapping("callback")
    public String callback(@RequestBody String msg) {
//        log.info("收到回调={}", msg);
        try {
            callBackService.callback(msg);
            return "success";
        } catch (Exception e) {
            log.error("回调异常={}", msg, e);
        }
        return "fail";
    }
}
