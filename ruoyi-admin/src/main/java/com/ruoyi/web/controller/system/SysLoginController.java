package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.dingtalk.DingTalkUserService;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.web.core.config.DingTalkConfig;
import com.taobao.api.ApiException;

import io.swagger.annotations.ApiOperation;

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
	private TokenService tokenService;

	@Autowired
	private DingTalkUserService dingtalkUserService;

	@Autowired
	private DingTalkConfig dingTalkConfig;

	/**
	 * 登录方法
	 * 
	 * @param loginBody
	 *            登录信息
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

	@ApiOperation("钉钉用户认证")
	@PostMapping("/dingTalkAuth")
	public AjaxResult dingTalkLogin(String authCode) {
		DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(dingTalkConfig.getAppKey());
		request.setAppsecret(dingTalkConfig.getAppSecret());
		request.setHttpMethod("GET");
		try {
			OapiGettokenResponse response = client.execute(request);
			String accessToken = response.getAccessToken();
			OapiUserGetResponse userInfo = dingtalkUserService.getUserInfo(authCode, accessToken);
			String mobile = userInfo.getMobile();
			if (mobile == null) {
				throw new CustomException("钉钉认证失败！");
			}
			AjaxResult ajax = AjaxResult.success();
			// 生成令牌
			Pair<String, LoginUser> userPair = loginService.loginByDingTalk(mobile);
			ajax.put(Constants.TOKEN, userPair.getLeft());
			LoginUser loginUser = userPair.getRight();
			ajax.put("user", loginUser.getUser());
			ajax.put("userType", loginUser.getUserType());
			return ajax;
		} catch (ApiException e) {
			return AjaxResult.error(e.getMessage());
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @return 用户信息
	 */
	@ApiOperation("根据token获取用户信息")
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
