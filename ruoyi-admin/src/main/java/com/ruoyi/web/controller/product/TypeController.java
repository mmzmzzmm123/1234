package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.ITypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "类型")
@RestController
@RequestMapping("/type")
public class TypeController {
    @Resource
    private ITypeService typeService;

    @ApiOperation("获取所有app类型")
    @GetMapping( "/app")
    public AjaxResult getAppList() {
        return AjaxResult.success(typeService.getAppList());
    }

    @ApiOperation("获取所有order类型")
    @GetMapping( "/order")
    public AjaxResult getOrderList() {
        return AjaxResult.success(typeService.getOrderList());
    }
}
