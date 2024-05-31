package com.onethinker.api;

import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.CodeTypeEnum;
import com.onethinker.common.enums.PlatformUserTypeEnum;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.service.IPlatformUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 平台用户接口
 * @author yangyouqi
 * @date : 2023/10/23 0023 21:00
 */
@RestController
@RequestMapping("/onethinker"+ ServicePathConstant.PREFIX_WX_API_PATH +"/platformUser")
public class PlatformUserController extends BaseController {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private IPlatformUserService platformUserService;

    private final PlatformUserTypeEnum userTypeEnum = PlatformUserTypeEnum.WX;

    /**
     * 平台用戶登录
     */
    @PostMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH +  "/login")
    public AjaxResult platformUserLogin(@RequestBody PlatformUserReqDTO reqDTO) {
        PlatformUserResDTO result = platformUserService.getUserStorage(userTypeEnum).login(reqDTO);
        return AjaxResult.success("登录成功", result);
    }

    /**
     * 平台用户注册
     * 目前平台注册不强制绑定手机号，邮箱等信息，而是通过账号密码进行注册，如微信小程序等有平台标识的，则直接通过对应平台进行登录
     * 绑定手机号，邮箱等信息则是通过平台我的信息那边进行绑定，接口{}
     * @param reqDTO
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_PUBLIC_PATH + "/register")
    public AjaxResult platformUserRegister(@RequestBody PlatformUserReqDTO reqDTO) {
        platformUserService.getUserStorage(userTypeEnum).register(reqDTO);
        return AjaxResult.success("注册成功");
    }

    /**
     * 绑定手机号
     * @param reqDTO
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/bindPhone")
    public AjaxResult bindPhone(@RequestBody PlatformUserReqDTO reqDTO) {
        platformUserService.bindPhoneOrEmail(reqDTO, CodeTypeEnum.PHONE);
        return AjaxResult.success("绑定账号成功");
    }

    /**
     * 绑定邮箱
     * @param reqDTO
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/bindEmail")
    public AjaxResult bindEmail(@RequestBody PlatformUserReqDTO reqDTO) {
        platformUserService.bindPhoneOrEmail(reqDTO,CodeTypeEnum.MAIL);
        return AjaxResult.success("绑定账号成功");
    }

    /**
     * 更新用户信息
     * @param platformUser 平台用户对象
     * @return
     */
    @PostMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH + "/update")
    public AjaxResult platformUserUpdate(@RequestBody PlatformUser platformUser) {
        PlatformUser resDTO = platformUserService.queryLoginUserInfo();
        platformUser.setId(resDTO.getId());
        platformUserService.updatePlatformUserDetail(platformUser);
        redisCache.deleteObject(CacheEnum.QUERY_USER_DETAIL_DATA_ID_KEY.getCode() + resDTO.getDataId());
        return AjaxResult.success("更新成功");
    }
}
