package com.ruoyi.api.controller;

import com.ruoyi.api.annotation.Login;
import com.ruoyi.api.annotation.LoginUser;
import com.ruoyi.api.utils.JwtUtils;
import com.ruoyi.data.core.R;
import com.ruoyi.data.domain.User;
import com.ruoyi.data.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Api(tags = "测试")
public class TestApiController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtils jwtUtils;

    @Login
    @GetMapping("/login")
    @ApiOperation("获取用户id")
    public R getUserId(@RequestAttribute("userId") Long userId){
        return R.success(userId);
    }

    @Login
    @GetMapping("/loginUser")
    @ApiOperation("获取用户")
    public R getUser(@LoginUser User user){
        return R.success(user);
    }

    @GetMapping
    @ApiOperation("无需认证")
    public R test(){
        return R.success("无需认证");
    }

    @PostMapping
    @ApiOperation("登录")
    public R login(){
        User user = userService.getById(0L);
        user.setToken(jwtUtils.generateToken(user.getId()));
        return R.success(user);
    }
}
