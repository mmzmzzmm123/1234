package com.ruoyi.framework.web.service;

import javax.annotation.Resource;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisKey;
import com.ruoyi.common.core.redis.RedisUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ISysUserService sysUserService;



    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
        // 用户验证
        Authentication authentication = null;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 注册账号
     *
     * @param username 用户名
     * @param password 密码
     * @param phone 手机号
     * @param phoneCode 手机号验证码
     * @param uuid 唯一标识
     * @param email 邮箱
     * @return 结果
     */
    public String registerUser(String username, String password, String phoneCode, String uuid, String email, String phone) {

        int usernameCount = sysUserService.countUserByUserName(username);
        if (usernameCount != 0) {
            return username + "登陆账号已经被使用,请更换!";
        }
        int phoneCount = sysUserService.countUserByPhone(phone);
        if (phoneCount != 0) {
            return phone + "手机账号已经被使用,请更换!";
        }

//        String key = RedisKey.BOOKMARK + RedisKey.REGISTER + phone;
//        //验证传入的手机号和验证码是否匹配
//        String redisPhoneCode = redisUtil.get(key);
//        if (StringUtils.isEmpty(redisPhoneCode)){
//            return  "验证码已经过期!";
//        }
//        if (!redisPhoneCode.equals(phoneCode)){
//            return  "验证码错误!";
//        }else {
//            //验证码正确 删除验证码 不可重复使用
//            redisUtil.delete(key);
//        }
        //加密信息存入数据库
        SysUser user = new SysUser();
        user.setUserName(username);
        user.setPassword(SecurityUtils.encryptPassword(password));
        user.setDeptId(110L);
        user.setNickName(username);
        user.setPhonenumber(phone);
        user.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        user.setUpdateBy("admin");
        user.setSex("2");
        user.setStatus("0");
        user.setPostIds(new Long[]{4L});
        user.setRoleIds(new Long[]{2L});//角色
        user.setAvatar("/profile/avatar/2020/11/01/8a354c2d-1b5a-475e-a9aa-04cb4a769323.jpeg");
        user.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        return addUser(user);
    }

    /**
     * 注册账号
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional
    public String addUser(SysUser user){

      int row = sysUserService.insertUser(user);
        SysUserServiceImpl sysUserService = new SysUserServiceImpl();
        //新增用户岗位关联
        sysUserService.insertUserPost(user);
        // 新增用户与角色管理
        sysUserService.insertUserRole(user);
      return row!=0?Constants.LOGIN_SUCCESS:Constants.LOGIN_FAIL;
    }
}
