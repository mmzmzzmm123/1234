package com.ruoyi.api.staff.controller;

import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.staff.model.vo.ApiStaffLevelConfigVo;
import com.ruoyi.api.staff.service.ApiStaffService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author LAM
 * @date 2023/9/20 16:05
 */
@Anonymous
@Api("api-员工信息相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staff")
public class ApiStaffController {

    private final ApiStaffService service;

    @ApiOperation("获取员工等级配置")
    @GetMapping("/selectStaffLevelConfig")
    public R<List<ApiStaffLevelConfigVo>> selectStaffLevelConfig(){
        return R.ok(service.selectStaffLevelConfig());
    }

    @ApiOperation("根据用户标识查询员工信息")
    @GetMapping("/selectByUserId")
    public R<ApiStaffInfoVo> selectByUserId(@RequestParam("userId") Long userId){
        return R.ok(service.selectByUserId(userId));
    }

    //public R<Boolean> apply()
}
