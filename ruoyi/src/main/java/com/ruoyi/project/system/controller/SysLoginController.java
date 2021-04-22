package com.ruoyi.project.system.controller;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.project.system.domain.BySchool;
import com.ruoyi.project.system.service.IBySchoolService;
import com.ruoyi.project.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginBody;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.SysLoginService;
import com.ruoyi.framework.security.service.SysPermissionService;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.project.system.domain.SysMenu;
import com.ruoyi.project.system.domain.SysUser;
import com.ruoyi.project.system.service.ISysMenuService;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {
    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String secret;

    @Value("${wx.domain}")
    private String domain;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService userService;


//    @Autowired
//    private RedisCache redisCache;

    /**
     * 登录方法
     *
     * @param username 用户名
     * @param password 密码
     * @param captcha  验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 登录方法
     *
     * @return 结果
     */
    @PostMapping("/wxlogin/{code}")
    public AjaxResult wxlogin(@PathVariable String code) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = "";
        String url = domain;
        String parmas = "appid=" + appid + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        System.out.println(parmas);
        String strResult = HttpUtils.sendGet(url, parmas);
        JSONObject jsonObject = JSONObject.parseObject(strResult);
        try {
            String openId = jsonObject.get("openid").toString();
            SysUser sysUser = userService.selectUserByWxId(openId);
            if (sysUser != null) {
                token = loginService.wxlogin(sysUser.getUserName(), sysUser.getPw());
            } else {
                return AjaxResult.error("登录失败");
            }
        } catch (Exception e) {
            return AjaxResult.error("登录失败");
        }
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

//    /**
//     * 将在其他地方登陆的账号强退
//     *
//     * @param user 登录信息
//     * @return 结果
//     */
//    @PostMapping("/forceLogout")
//    public AjaxResult forcelogin(@RequestBody LoginUser user)
//    {
//        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + user.getToken());
//
//        return AjaxResult.success();
//    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SysUser user = loginUser.getUser();
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
    public AjaxResult getRouters() {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getUserId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
