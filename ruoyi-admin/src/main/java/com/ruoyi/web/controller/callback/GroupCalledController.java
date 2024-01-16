package com.ruoyi.web.controller.callback;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.GroupCallPlayDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 群业务相关的回调
 */
@Slf4j
@RestController
@RequestMapping("/groupCall")
public class GroupCalledController {
    /**
     * 群成员变动
     */
    @Anonymous
    @PostMapping( "/member")
    public R<String> getAppList(@RequestBody GroupCallPlayDTO dto) {
        log.info("GroupCalledController:{}", JSON.toJSON(dto));
        //todo 写入t_play_group_info
        return R.ok("123");
    }
}
