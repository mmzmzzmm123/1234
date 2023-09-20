package com.ruoyi.api.user.controller;

import com.ruoyi.api.user.model.dto.ApiUserDto;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.api.user.service.ApiUserService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author LAM
 * @date 2023/9/19 17:02
 */
@Anonymous
@Api("api-用户相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ApiUserController {

    private final ApiUserService service;

    @ApiOperation("小程序用户登录")
    @PostMapping("/wxMiniLogin")
    public R<ApiUserVo> wxMiniLogin(@RequestParam("code") String code){
        return service.wxMiniLogin(code);
    }

    @ApiOperation("用户信息更新")
    @PostMapping("/update")
    public R<Boolean> update(@Valid ApiUserDto dto){
        return R.ok(service.update(dto));
    }

    @ApiOperation("生成用户推荐码")
    @GetMapping("/generateReferralCode")
    public R<String> generateReferralCode(@RequestParam("userId") Long userId){
        return R.ok(service.generateReferralCode(userId));
    }
}
