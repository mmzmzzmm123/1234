package com.ruoyi.web.controller.system;

import java.security.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.annotation.DecryptLogin;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.LimitType;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;

/**
 * 登录验证
 * 
 * @author ruoyi
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录前先生成一个RSA密钥，LoginBody中的账号密码进行加密。
     * 公钥返回给前端加密使用；
     * 私钥存入redis，等待登陆时解密使用。
     *
     * 这个接口需要加入白名单，所以使用限流器 RateLimiter 对IP限制每秒请求次数
     * @return public key
     */
    @RateLimiter(time = 1, count = 5, limitType = LimitType.IP)
    @GetMapping("/preLogin")
    public AjaxResult preLogin() {
        String publicKey;
        try {
            publicKey = loginService.generateRSA();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            return AjaxResult.error("生成RSA密钥对失败");
        }
        return AjaxResult.success("", publicKey);
    }

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @DecryptLogin
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
