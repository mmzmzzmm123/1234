package com.ruoyi.web.controller.onethinker;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;
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
    @PostMapping(value = "/login")
    public AjaxResult platformUserLogin(@RequestBody PlatformUserReqDTO reqDTO) {
        PlatformUserResDTO result = platformUserService.platformUserServiceLogin(reqDTO);
        return AjaxResult.success("登录成功",result);
    }
}
