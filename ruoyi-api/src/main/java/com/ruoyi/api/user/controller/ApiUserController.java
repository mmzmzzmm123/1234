package com.ruoyi.api.user.controller;

import com.ruoyi.api.user.model.dto.ApiUserDto;
import com.ruoyi.api.user.model.vo.ApiUserLikeDataVo;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.api.user.service.ApiUserService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.RepeatSubmit;
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
    public R<ApiUserVo> wxMiniLogin(@RequestParam("code") String code) {
        return service.wxMiniLogin(code);
    }

    @ApiOperation("获取最新的用户信息")
    @GetMapping("/getNewUserInfo")
    public R<ApiUserVo> getNewUserInfo() {
        return R.ok(service.getUserInfo());
    }

    @ApiOperation("用户信息更新")
    @PostMapping("/update")
    public R<Boolean> update(@Valid ApiUserDto dto) {
        return R.ok(service.update(dto));
    }

    @ApiOperation("生成用户推荐码")
    @GetMapping("/generateReferralCode")
    public R<String> generateReferralCode() {
        return R.ok(service.generateReferralCode());
    }

    @ApiOperation("获取用户点赞收藏记录数据")
    @GetMapping("/selectUserLikeData")
    public R<ApiUserLikeDataVo> selectUserLikeData() {
        return R.ok(service.selectUserLikeData());
    }

    @ApiOperation("用户关注店员")
    @GetMapping("/likeStaff")
    public R<Boolean> likeStaff(@RequestParam("staffId") Long staffId){
        return R.ok(service.likeStaff(staffId));
    }

    @ApiOperation("用户取消关注店员")
    @GetMapping("/cancelLikeStaff")
    public R<Boolean> cancelLikeStaff(@RequestParam("staffId") Long staffId){
        return R.ok(service.cancelLikeStaff(staffId));
    }
}
