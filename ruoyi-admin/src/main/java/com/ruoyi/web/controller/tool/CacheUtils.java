package com.ruoyi.web.controller.tool;


import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("cacheUtils")
public class CacheUtils {

    @Autowired
    private RedisCache redisCache;


    public void Cache(){
        ISysMenuService bean = SpringUtils.getBean(ISysMenuService.class);
        ISysUserService bean1 = SpringUtils.getBean(ISysUserService.class);
        List<SysMenu> sysMenus = bean.selectMenuList(1L);
        redisCache.setCacheList("meunu",sysMenus);
    }
}
