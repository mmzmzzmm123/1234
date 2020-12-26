package com.gox.framework.web.service;

import javax.annotation.Resource;

import com.gox.common.core.domain.entity.SysUser;
import com.gox.common.utils.StringUtils;
import com.gox.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.gox.common.constant.Constants;
import com.gox.common.core.domain.model.LoginUser;
import com.gox.common.core.redis.RedisCache;
import com.gox.common.exception.CustomException;
import com.gox.common.exception.user.CaptchaException;
import com.gox.common.exception.user.CaptchaExpireException;
import com.gox.common.exception.user.UserPasswordNotMatchException;
import com.gox.common.utils.MessageUtils;
import com.gox.framework.manager.AsyncManager;
import com.gox.framework.manager.factory.AsyncFactory;

import java.util.concurrent.TimeUnit;

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
    private ISysUserService userService;

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
        String loginErrKey = "login_err:"+username;
        Object err = redisCache.getCacheObject(loginErrKey);
        int loginErrNum = redisCache.getCacheObject("loginErrNum");
        int loginLockMinutes = redisCache.getCacheObject("loginLockMinutes");
        int errs = 0;
        if (err!=null){
            errs = (int) err;
        }
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
        if (errs>=4){
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.retry.limit.exceed"),loginLockMinutes));
            SysUser user = userService.selectUserByUserName(username);
            if ("0".equals(user.getStatus())){
                user.setStatus("1");
                userService.updateUserStatus(user);
            }
            throw new CustomException("密码错误"+loginErrNum+"次,账号锁定"+loginLockMinutes+"分钟");
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
                redisCache.increment(loginErrKey,1,30,TimeUnit.MINUTES);
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
}
