package com.ruoyi.project.mr.api;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * swagger 基础信息方法
 *
 * @author ruoyi
 */
@Api(tags = "基础信息")
@RestController
@RequestMapping("/mr/base")
public class MRBaseController extends BaseController {
    // 令牌秘钥
    @Value("${token.apiSecret}")
    private String apiSecret;

    @Autowired
    private ISysUserService userService;

    @ApiOperation(value = "判断登陆", notes = "根据账号密码判断是否存在")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", dataType = "string", paramType = "query", example = "admin"),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "string", paramType = "query", example = "123456")})
    @GetMapping("/login")
    public AjaxResult getUser(@RequestHeader(value = "signature", required = false) String secret, String name, String password) {
        if (StringUtils.isEmpty(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        } else if (!apiSecret.equals(secret)) {
            return AjaxResult.error(400, MessageUtils.message("api.error.msg"));
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(name))) {
            SysUser user = userService.selectUserByUserName(name);
            if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
                return AjaxResult.error(400, MessageUtils.message("user.password.delete"));
            } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
                return AjaxResult.error(400, MessageUtils.message("user.blocked"));
            } else if (!SecurityUtils.matchesPassword(password,user.getPassword())) {
                return AjaxResult.error(400, MessageUtils.message("user.not.exists"));
            } else {
                return AjaxResult.success(user);
            }
        } else {
            return AjaxResult.error(MessageUtils.message("user.not.exists"));
        }

    }
}
