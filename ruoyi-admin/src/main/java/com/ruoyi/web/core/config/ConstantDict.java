package com.ruoyi.web.core.config;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysMenuMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.impl.SysDeptServiceImpl;
import com.ruoyi.system.service.impl.SysMenuServiceImpl;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author qianyang
 * @create 2022/09/09 14:54
 */
@Component
public class ConstantDict {

    @Autowired
    private RedisCache redisCache;

    @PostConstruct
    public void cache(){
        SysUserMapper userMapper = SpringUtils.getBean(SysUserMapper.class);
        SysMenuMapper menuMapper = SpringUtils.getBean(SysMenuMapper.class);

        List<SysUser> sysUsers = userMapper.selectUserList(new SysUser());
        List<SysMenu> sysMenus = menuMapper.selectMenuList(new SysMenu());
        System.out.println("ConstantDict 启动方法");
        redisCache.setCacheObject("sys-user1",sysUsers);
        redisCache.setCacheObject("sys-menu1",sysMenus);
    }
}
