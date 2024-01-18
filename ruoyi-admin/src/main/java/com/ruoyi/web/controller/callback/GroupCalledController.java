package com.ruoyi.web.controller.callback;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.callback.BotCallBackService;
import com.ruoyi.system.service.impl.GroupCalledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 群业务相关的回调
 */
@Slf4j
@RestController
@RequestMapping("/groupCall")
public class GroupCalledController {


    @Resource
    private BotCallBackService botCallBackService;

    /**
     * 群成员变动
     */
    @Anonymous
    @PostMapping( "/member")
    public R<Boolean> updateMember(@RequestBody String str) {
        log.info("GroupCalledController:{}", str);
        botCallBackService.callback(str);
        return R.ok();
    }

}
