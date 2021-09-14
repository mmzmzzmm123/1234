package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.wxapi.service.IWxUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private IWxUserService wxUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        if (StringUtils.isNotEmpty(username) && username.startsWith("wx_openid_")){
            String openId = username.replace("wx_openid_", "");
            WxUser wxUser = wxUserService.selectWxUserByOpenId(openId);
            wxUser.setPassword(SecurityUtils.encryptPassword(wxUser.getOpenId()));
            if (StringUtils.isNull(wxUser))
            {
                log.info("登录用户：{} 不存在.", openId);
                throw new ServiceException("登录用户不存在");
            }
            else if ("3".equals(wxUser.getStatus()))
            {
                log.info("登录用户：{} 已被停用.", openId);
                throw new ServiceException("对不起，您的账号已停用");
            }

            return new LoginUser(wxUser);
        }


        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
