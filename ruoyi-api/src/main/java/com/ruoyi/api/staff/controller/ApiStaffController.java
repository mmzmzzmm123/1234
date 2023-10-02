package com.ruoyi.api.staff.controller;

import com.ruoyi.api.staff.model.dto.ApiStaffInfoDto;
import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.staff.model.vo.ApiStaffLevelConfigVo;
import com.ruoyi.api.staff.model.vo.ApiStaffPhotoVo;
import com.ruoyi.api.staff.service.ApiStaffService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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

    @ApiOperation("根据用户标识查询员工相册")
    @GetMapping("/selectPhotoByUserId")
    public R<List<ApiStaffPhotoVo>> selectPhotoByUserId(@RequestParam("userId") Long userId){
        return R.ok(service.selectPhotoByUserId(userId));
    }

    @ApiOperation("申请成为店员")
    @PostMapping("/apply")
    public R<Boolean> apply(@Valid ApiStaffInfoDto dto){
        return service.apply(dto);
    }

    @ApiOperation("修改数据")
    @PostMapping("/update")
    public R<Boolean> update(@Valid ApiStaffInfoDto dto){
        return service.update(dto);
    }

    @ApiOperation("获取店员配置接单服务id")
    @GetMapping("/selectServiceConfigIds")
    public R<List<Long>> selectServiceConfigIds(@RequestParam("userId") Long userId){
        return R.ok(service.selectServiceConfigIds(userId));
    }
}
