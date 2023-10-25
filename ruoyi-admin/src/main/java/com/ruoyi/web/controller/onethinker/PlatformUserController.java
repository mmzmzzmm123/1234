package com.ruoyi.web.controller.onethinker;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.domain.PlatformUserDetail;
import com.ruoyi.onethinker.dto.PlatformUserReqDTO;
import com.ruoyi.onethinker.dto.PlatformUserResDTO;
import com.ruoyi.onethinker.service.IPlatformUserDetailService;
import com.ruoyi.onethinker.service.IPlatformUserService;
import com.ruoyi.onethinker.service.PlatformUserFactory;

/**
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:00
 */
@RestController
@RequestMapping("/onethinker/platformUser")
public class PlatformUserController extends BaseController {

    @Autowired
    private PlatformUserFactory platformUserFactory;

    @Autowired
    private IPlatformUserDetailService platformUserDetailService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 平台用戶登錄
     */
    @PostMapping(value = "/login")
    public AjaxResult platformUserLogin(@RequestBody PlatformUserReqDTO reqDTO) {
        IPlatformUserService platformUserService = platformUserFactory.queryPlatformUserServiceBySourceType(reqDTO.getSourceType());
        PlatformUserResDTO result = platformUserService.login(reqDTO);
        return AjaxResult.success("登录成功",result);
    }

    @PostMapping(value = "/register")
    public AjaxResult platformUserRegister(@RequestBody PlatformUserReqDTO reqDTO) {
        IPlatformUserService platformUserService = platformUserFactory.queryPlatformUserServiceBySourceType(reqDTO.getSourceType());
        platformUserService.register(reqDTO);
        return AjaxResult.success("注册成功");
    }

    @PostMapping(value = "/update")
    public AjaxResult platformUserUpdate(@RequestBody PlatformUserDetail platformUserDetail) {
        PlatformUserDetail resDTO = platformUserDetailService.queryLoginUserInfo();
        platformUserDetail.setId(resDTO.getId());
        platformUserDetailService.updatePlatformUserDetail(platformUserDetail);
        redisCache.deleteObject(CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + resDTO.getDataId());
        return AjaxResult.success("更新成功");
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    @PreAuthorize("@ss.hasPermi('onethinker:user:query')")
    @GetMapping(value = "/queryLoginUserInfo")
    public AjaxResult queryLoginUserInfo() {
        PlatformUserDetail resDTO = platformUserDetailService.queryLoginUserInfo();
        return AjaxResult.success(resDTO);
    }


}
