package com.boot.web.controller.system;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.convert.Convert;
import com.boot.chatgpt.config.WxConfiguration;
import com.boot.common.constant.Constants;
import com.boot.common.core.domain.AjaxResult;
import com.boot.common.core.domain.entity.SysMenu;
import com.boot.common.core.domain.entity.SysUser;
import com.boot.common.core.domain.model.LoginBody;
import com.boot.common.core.domain.model.WXLoginBody;
import com.boot.common.core.domain.model.WXRegisterBody;
import com.boot.common.utils.SecurityUtils;
import com.boot.common.utils.ServletUtils;
import com.boot.framework.web.service.SysLoginService;
import com.boot.framework.web.service.SysPermissionService;
import com.boot.framework.web.service.SysRegisterService;
import com.boot.system.service.ISysConfigService;
import com.boot.system.service.ISysMenuService;
import com.boot.system.service.ISysUserService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 * 
 * @author boot
 */
@Controller
public class SysLoginController {
	/**
	 * 是否开启记住我功能
	 */
	@Value("${shiro.rememberMe.enabled: false}")
	private boolean rememberMe;

	@Autowired
	private ISysConfigService configService;
    @Autowired
    private SysLoginService loginService;
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private SysPermissionService permissionService;
	@Autowired
	private SysRegisterService registerService;
	@Autowired
	private ISysUserService userService;
	@PostMapping("/loginByOpenId")
	@ResponseBody
	public AjaxResult loginByOpenId(@RequestBody WXLoginBody wxLoginBody){
		try {
			AjaxResult ajax = AjaxResult.success();
			String code = wxLoginBody.getCode();
			String appid = wxLoginBody.getAppid();
			WxMaJscode2SessionResult jscode2session = WxConfiguration.getMaService(appid).jsCode2SessionInfo(code);
			String openid = jscode2session.getOpenid();
			String unionid = jscode2session.getUnionid();
			SysUser sysUser = userService.selectUserByUserName(openid);
			if(null == sysUser){
				WXRegisterBody wxRegisterBody = new WXRegisterBody();
				wxRegisterBody.setOpenId(openid);
				wxRegisterBody.setUsername(openid);
				wxRegisterBody.setPassword(openid);
				wxRegisterBody.setUnionId(unionid);
				registerService.register(wxRegisterBody);
			}
			String token = loginService.login(wxLoginBody.getUsername(), wxLoginBody.getPassword());
			ajax.put(Constants.TOKEN, token);
			return ajax;
		} catch (WxErrorException e) {
			throw new RuntimeException(e);
		}
	}
	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, ModelMap mmap)
	{
		// 如果是Ajax请求，返回Json字符串。
		if (ServletUtils.isAjaxRequest(request))
		{
			ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
			return null;
		}
		// 是否开启记住我
		mmap.put("isRemembered", rememberMe);
		mmap.put("captchaEnabled", configService.selectConfigByKey("sys.account.captchaEnabled"));
		// 是否开启用户注册
		mmap.put("isAllowRegister", Convert.toBool(configService.selectConfigByKey("sys.account.registerUser"), false));
		return "login";
	}


    /**
     * 登录方法
     * 
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult login(@RequestBody LoginBody loginBody)
//    public AjaxResult login(@RequestParam("username") String username, @RequestParam("password") String password)
//    {
//		AjaxResult ajax = AjaxResult.success();
//		// 生成令牌
//		String token = loginService.login(username, password);
//		ajax.put(Constants.TOKEN, token);
//		return ajax;
//	}
    {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }
	@GetMapping("/unauth")
	public String unauth()
	{
		return "error/unauth";
	}

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    @ResponseBody
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
    @ResponseBody
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
