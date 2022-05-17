package com.ruoyi.framework.web.service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.PasswordConfig;
import com.ruoyi.system.service.ISysPasswordService;
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

import java.util.Date;
import java.util.Optional;

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
    private ISysPasswordService passwordService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        PasswordConfig passwordConfig = passwordService.selectConfig();

        String message=null;
        if (StringUtils.isNull(user))
        {
            message = StringUtils.format("登录用户：{} 不存在",username);
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            message =                    StringUtils.format("登录用户：{} 已被删除.", username);
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            message =             StringUtils.format        ("登录用户：{} 已被停用.", username);
        }
        else if(System.currentTimeMillis() -user.getLockTime().getTime() <1000*60*passwordConfig.getUserLockPeriod()     )
        {
            String lockTimeStr = DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",user.getLockTime() );
            message = StringUtils.format("登录用户：{}已经于{}被锁定，请于{}分钟后重试",username,lockTimeStr,passwordConfig.getUserLockPeriod());
        }
        else if(DateUtils.addDays(Optional.ofNullable(user.getUpdateTime()).orElse(DateUtils.addYears(new Date(),-1)),passwordConfig.getPasswordExpirationPeriodDays()).compareTo(new Date()) <= 0 )
        {
            message =StringUtils.format("登录用户：{} 密码已经超过{}天未修改，请联系管理员重置密码.", username,passwordConfig.getPasswordExpirationPeriodDays());
        }
        if(message!=null)
        {
            log.info(message);
            throw new ServiceException(message);
        }

        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
