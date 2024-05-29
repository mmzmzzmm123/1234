package com.onethinker.web.controller.system;

import com.onethinker.common.config.OnethinkerConfig;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 首页
 *
 * @author yangyouqi
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Autowired
    private OnethinkerConfig onethinkerConfig;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，请通过前端地址访问。", onethinkerConfig.getName());
    }
}
