package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.system.mapper.SysMenuMapper;
import com.ruoyi.system.service.ISysLogininforService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.impl.SysMenuServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysLogininforControllerTest {

    @Autowired
    private SysMenuMapper menuMapper;

    @Resource
    private SysMenuServiceImpl sysMenuServiceImpl;

    @Test
    public void equalTest() {
        List<SysMenu> sysMenus = menuMapper.selectMenuTreeAll();
        assertIterableEquals(sysMenuServiceImpl.getChildPermsByMap(sysMenus, 0),
                sysMenuServiceImpl.getChildPerms(sysMenus, 0));
    }

}