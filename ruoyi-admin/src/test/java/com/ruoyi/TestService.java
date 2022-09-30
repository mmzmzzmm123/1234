package com.ruoyi;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.framework.web.domain.server.Sys;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author qianyang
 * @create 2022/09/09 15:37
 */
@SpringBootTest
public class TestService {

    @Autowired
    ISysUserService sysUserService;

    @Test
    public void test(){

        SysUser sysUser = new SysUser();
        List<SysUser> sysUsers = sysUserService.selectUserList(sysUser);
        for (SysUser user : sysUsers) {

            System.out.println(user);

        }

    }
}
