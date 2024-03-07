package com.ruoyi.web.controller.user;

import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.dto.PlatformUserReqDTO;
import com.onethinker.user.dto.PlatformUserResDTO;
import com.onethinker.user.factory.PlatformUserFactory;
import com.onethinker.user.factory.service.IPlatformUserService;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        return AjaxResult.success("登录成功", result);
    }

    @PostMapping(value = "/register")
    public AjaxResult platformUserRegister(@RequestBody PlatformUserReqDTO reqDTO) {
        IPlatformUserService platformUserService = platformUserFactory.queryPlatformUserServiceBySourceType(reqDTO.getSourceType());
        return AjaxResult.success(platformUserService.register(reqDTO));
    }

    /**
     * 忘记密码 获取验证码
     * <p>
     * 1 手机号
     * 2 邮箱
     */
    @GetMapping("/getCodeForForgetPassword")
    public AjaxResult getCodeForForgetPassword(@RequestParam("place") String place, @RequestParam("flag") String flag) {
        /**
         * 这里只有网页版才有
         */
        IPlatformUserService platformUserService = platformUserFactory.queryPlatformUserServiceBySourceType(PlatformUserReqDTO.SOURCE_TYPE_ACCOUNT);
        platformUserService.getCodeForForgetPassword(place, flag);
        return AjaxResult.success();
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
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('onethinker:user:query')")
    @GetMapping(value = "/queryLoginUserInfo")
    public AjaxResult queryLoginUserInfo() {
        PlatformUserDetail resDTO = platformUserDetailService.queryLoginUserInfo();
        return AjaxResult.success(resDTO);
    }


}
