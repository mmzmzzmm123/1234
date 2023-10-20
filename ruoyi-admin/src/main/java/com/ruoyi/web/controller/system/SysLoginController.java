package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Set;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.office.domain.TWxUserCleaner;
import com.ruoyi.office.domain.vo.BindingRoleReq;
import com.ruoyi.office.domain.vo.MerchantBindingReq;
import com.ruoyi.office.service.ITStoreUserService;
import com.ruoyi.office.service.ITWxUserCleanerService;
import io.swagger.annotations.ApiOperation;
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
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ITWxUserCleanerService cleanerService;

    @Autowired
    private ITStoreUserService storeUserService;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
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
     * 微信用户登录
     */
//    @Log(title = "微信用户信息", businessType = BusinessType.INSERT)
    @PostMapping("/wxLogin")
    public AjaxResult wxlogin(@RequestBody WxLoginBody loginBody) {
        String token = loginService.wxMaLogin(loginBody);
        AjaxResult ajax = AjaxResult.success();
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        AjaxResult ajax = AjaxResult.success();
        if(loginUser.getUser() != null){
            SysUser user  = loginUser.getUser();
            ajax.put("user", user);
            // 角色集合
            Set<String> roles = permissionService.getRolePermission(user);
            ajax.put("roles", roles);
            // 权限集合
            Set<String> permissions = permissionService.getMenuPermission(user);
            ajax.put("permissions", permissions);
        }
        if(loginUser.getWxUser() != null){
            ajax.put("wxUser", loginUser.getWxUser());
            ajax.put("storeRoles", permissionService.getStoreRoles(loginUser.getWxUser()));
        }
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }


    /**
     * 绑定后台账号
     *
     * @return
     */
    @ApiOperation("商家小程序绑定后台账号")
    @PostMapping("/binding")
    public AjaxResult binding(@RequestBody MerchantBindingReq bindingReq) {
        loginService.binding(bindingReq, SecurityUtils.getLoginUser());
        return AjaxResult.success();
    }

    @ApiOperation("绑定店铺授权")
    @Log(title = "绑定店铺授权", businessType = BusinessType.INSERT)
    @PostMapping("/binding/role")
    public AjaxResult bindingRole(@RequestBody BindingRoleReq req) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        try {
            TWxUserCleaner cleaner = cleanerService.selectTWxUserCleanerById(req.getId());
            if(cleaner.getStatus() != null && cleaner.getStatus() == 1l){
                throw new ServiceException("请联系商家，确认是否已经添加！");
            }
            cleaner.setWxUserId(loginUser.getWxUser().getId());
            cleaner.setStatus(1l);
            cleanerService.updateTWxUserCleaner(cleaner);

            storeUserService.bind(loginUser.getWxUser().getId(), cleaner.getStoreId(), cleaner.getRemark());
            loginUser.setStoreRoles(permissionService.getStoreRoles(loginUser.getWxUser()));
            tokenService.refreshToken(loginUser);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
