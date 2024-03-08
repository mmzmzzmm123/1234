package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.domain.VehicleUserinfo;
import com.ruoyi.system.mapper.VehicleUserinfoMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.IVehicleUserinfoService;
import com.ruoyi.system.vo.LoginBody;
import com.ruoyi.system.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 小程序用户信息管理Service业务层处理
 * 
 * @author carol
 * @date 2024-03-01
 */
@Service
public class VehicleUserinfoServiceImpl implements IVehicleUserinfoService
{
    private static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private VehicleUserinfoMapper vehicleUserinfoMapper;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private TokenService tokenService;
    /**
     * 查询小程序用户信息管理
     * 
     * @param id 小程序用户信息管理主键
     * @return 小程序用户信息管理
     */
    @Override
    public VehicleUserinfo selectVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoById(id);
    }

    /**
     * 查询小程序用户信息管理列表
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 小程序用户信息管理
     */
    @Override
    public List<VehicleUserinfo> selectVehicleUserinfoList(VehicleUserinfo vehicleUserinfo)
    {
        return vehicleUserinfoMapper.selectVehicleUserinfoList(vehicleUserinfo);
    }

    /**
     * 新增小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    @Override
    public int insertVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setCreateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.insertVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 修改小程序用户信息管理
     * 
     * @param vehicleUserinfo 小程序用户信息管理
     * @return 结果
     */
    @Override
    public int updateVehicleUserinfo(VehicleUserinfo vehicleUserinfo)
    {
        vehicleUserinfo.setUpdateTime(DateUtils.getNowDate());
        return vehicleUserinfoMapper.updateVehicleUserinfo(vehicleUserinfo);
    }

    /**
     * 批量删除小程序用户信息管理
     * 
     * @param ids 需要删除的小程序用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoByIds(Long[] ids)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoByIds(ids);
    }

    /**
     * 删除小程序用户信息管理信息
     * 
     * @param id 小程序用户信息管理主键
     * @return 结果
     */
    @Override
    public int deleteVehicleUserinfoById(Long id)
    {
        return vehicleUserinfoMapper.deleteVehicleUserinfoById(id);
    }

    /**
     * 微信小程序登录
     * @param loginBody
     * @return
     */
    @Override
    public LoginVO wxLogin(LoginBody loginBody) {
        String openid = getOpenid(loginBody.getCode());
        //判断openid是否为空，若为空表示登录失败，抛出异常
        if(StringUtils.isEmpty(openid)){
            throw new RuntimeException("登录失败");
        }
        //判断当前用户是否为新用户
        VehicleUserinfo userinfo=vehicleUserinfoMapper.getUserByOpenId(openid);
        //若为新用户，自动完成注册
        if(ObjectUtils.isEmpty(userinfo)){
            userinfo=VehicleUserinfo.builder()
                    .nickName(loginBody.getUsername())
                    .openid(openid)
                    .avatarUrl(loginBody.getAvatarUrl())
                    .build();
            vehicleUserinfoMapper.insert(userinfo);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( "wx"+openid,"admin123");
            AuthenticationContextHolder.setContext(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(openid, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(openid, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(openid, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //recordLoginInfo(loginUser.getUserId());
        // 生成token
        loginUser.setUserId(userinfo.getId());
        loginUser.setObject(userinfo);
        String token = tokenService.createWxToken(loginUser);
        return LoginVO.builder().userId(userinfo.getId()).token(token).openid(openid).build();
    }

    /**
     * 获取openid
     * @param code
     * @return
     */
    private String getOpenid(String code) {
        //调用微信接口服务，获取当前微信用户的openid
        String param="appid=wx21ed1820a1cca888&secret=6fef6125fb5323f79174ed9a68ad8717&js_code="+code+"&grant_type=authorization_code";
        String resp = HttpUtils.sendGet(WX_LOGIN, param);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        String openid = (String) jsonObject.get("openid");
        System.out.println("返回的数据:"+resp+"====>"+openid);
        return openid;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr());
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }
}
