package com.ruoyi.api.user.controller;

import com.ruoyi.api.user.model.dto.ApiUserAccountFormDto;
import com.ruoyi.api.user.model.vo.ApiUserAccountVo;
import com.ruoyi.api.user.service.ApiUserAccountService;
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
 * @date 2023/9/21 20:00
 */
@Anonymous
@Api("api-用户账号管理相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/userAccount")
public class ApiUserAccountController {

    private final ApiUserAccountService service;

    @ApiOperation("获取用户账号列表")
    @GetMapping("/selectByUserId")
    public R<List<ApiUserAccountVo>> selectByUserId(){
        return R.ok(service.selectByUserId());
    }

    @ApiOperation("新增用户账号")
    @PostMapping("/add")
    public R<Boolean> add(ApiUserAccountFormDto dto){
        return service.add(dto);
    }

    @ApiOperation("更新用户账号")
    @PostMapping("/update")
    public R<Boolean> update(@Valid ApiUserAccountFormDto dto){
        return service.update(dto);
    }

    @ApiOperation("删除用户账号")
    @PostMapping("/deleteById")
    public R<Boolean> deleteById(@RequestParam("id") Long id){
        return R.ok(service.deleteById(id));
    }
}
