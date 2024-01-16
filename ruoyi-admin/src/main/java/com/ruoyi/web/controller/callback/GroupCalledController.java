package com.ruoyi.web.controller.callback;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.GroupCallPlayDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 群业务相关的回调
 */
@Slf4j
@RequestMapping("/groupCall")
public class GroupCalledController {
    /**
     * 群信息回调
     */
    @Anonymous
    @PostMapping("/play")
    public R<String> play(@RequestBody GroupCallPlayDTO dto) {
        log.info("GroupCalledController:{}", dto.toString());
        //todo 写入t_play_group_info
        return R.ok();
    }
}
