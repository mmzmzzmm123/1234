package com.onethinker.web.controller.user;

import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.user.service.IPlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 平台用户接口
 * @author : yangyouqi
 * @date : 2023/10/23 0023 21:00
 */
@RestController
@RequestMapping("/onethinker/platformUser")
public class PlatformUserController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IPlatformUserService platformUserService;

    /**
     * 平台用戶登录
     * @param reqDTO
     * @return
     */
    @PostMapping(value = "/login")
    public AjaxResult platformUserLogin(@RequestBody PlatformUserReqDTO reqDTO) {
        PlatformUserResDTO result = platformUserService.getUserStorage(reqDTO.getSourceType()).login(reqDTO);
        return AjaxResult.success("登录成功", result);
    }

    /**
     * 平台用户注册
     * 目前平台注册不强制绑定手机号，邮箱等信息，而是通过账号密码进行注册，如微信小程序等有平台标识的，则直接通过对应平台进行登录
     * 绑定手机号，邮箱等信息则是通过平台我的信息那边进行绑定，接口{}
     * @param reqDTO
     * @return
     */
    @PostMapping(value = "/register")
    public AjaxResult platformUserRegister(@RequestBody PlatformUserReqDTO reqDTO) {
        platformUserService.getUserStorage(reqDTO.getSourceType()).register(reqDTO);
        return AjaxResult.success("注册成功");
    }

    /**
     * 绑定手机号或邮箱
     * @param reqDTO
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/bindPhoneOrEmail")
    public AjaxResult bindPhoneOrEmail(@RequestBody PlatformUserReqDTO reqDTO) {
        platformUserService.bindPhoneOrEmail(reqDTO);
        return AjaxResult.success("绑定账号成功");
    }

    /**
     * 更新用户信息
     * @param platformUser 平台用户对象
     * @return
     */
    @PostMapping(value = "/update")
    public AjaxResult platformUserUpdate(@RequestBody PlatformUser platformUser) {
        PlatformUser resDTO = platformUserService.queryLoginUserInfo();
        platformUser.setId(resDTO.getId());
        platformUserService.updatePlatformUserDetail(platformUser);
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
        PlatformUser resDTO = platformUserService.queryLoginUserInfo();
        return AjaxResult.success(resDTO);
    }


}
