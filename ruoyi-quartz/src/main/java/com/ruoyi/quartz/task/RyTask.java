package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask {
    @Autowired
    private ISysUserService userService;

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
        SysUser user = new SysUser();
        user.setPassword(params);
        List<SysUser> list = userService.selectUserListTask(user);
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                user = list.get(i);
                userService.checkUserAllowed(user);
                user.setPassword(SecurityUtils.encryptPassword("123456"));
                user.setUpdateBy("admin");
                userService.resetPwd(user);
            }
        }
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
