package com.onethinker.web.controller.user;

import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.common.enums.CodeTypeEnum;
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
     * @param reqDTO
     * @return
     */
    @PostMapping(value = "/register")
    public AjaxResult platformUserRegister(@RequestBody PlatformUserReqDTO reqDTO) {
        PlatformUserResDTO result = platformUserService.getUserStorage(reqDTO.getSourceType()).register(reqDTO);
        return AjaxResult.success(result);
    }

    /**
     * 忘记密码 获取验证码
     * <p>
     * 1 手机号
     * 2 邮箱
     */
    @GetMapping("/getCodeForForgetPassword")
    public AjaxResult getCodeForForgetPassword(@RequestParam("place") String place, @RequestParam("flag") CodeTypeEnum flag) {
        platformUserService.getCodeForForgetPassword(place,flag);
        return AjaxResult.success();
    }

    /**
     * 更新用户信息
     * @param platformUserDetail
     * @return
     */
    @PostMapping(value = "/update")
    public AjaxResult platformUserUpdate(@RequestBody PlatformUserDetail platformUserDetail) {
        PlatformUserDetail resDTO = platformUserService.queryLoginUserInfo();
        platformUserDetail.setId(resDTO.getId());
        platformUserService.updatePlatformUserDetail(platformUserDetail);
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
        PlatformUserDetail resDTO = platformUserService.queryLoginUserInfo();
        return AjaxResult.success(resDTO);
    }


}
