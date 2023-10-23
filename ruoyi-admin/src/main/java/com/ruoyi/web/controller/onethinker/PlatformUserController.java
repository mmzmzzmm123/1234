package com.ruoyi.web.controller.onethinker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.onethinker.service.IPlatformUserService;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:00
 */
@RestController
@RequestMapping("/onethinker/platformUser")
public class PlatformUserController extends BaseController {

    @Autowired
    private IPlatformUserService platformUserService;


    /**
     * 查询平台用户列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:PlatformUser:list')")
    @GetMapping("/list")
    public TableDataInfo list(PlatformUser platformUser) {
        startPage();
        List<PlatformUser> list = platformUserService.selectPlatformUserList(platformUser);
        return getDataTable(list);
    }
}
