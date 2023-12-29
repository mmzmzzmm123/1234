package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.vo.TypeVO;
import com.ruoyi.system.service.ITypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : zengyi
 */
@Api(tags = "类型")
@RestController
@RequestMapping("/type")
public class TypeController {
    @Resource
    private ITypeService typeService;

    @Anonymous
    @ApiOperation("获取所有app类型")
    @GetMapping( "/app")
    public R<List<TypeVO>> getAppList() {
        return R.ok(typeService.getAppList());
    }

    @Anonymous
    @ApiOperation("获取所有商品类型")
    @GetMapping( "/productCategory")
    public R<List<TypeVO>> getProductCategoryList() {
        return R.ok(typeService.getProductCategoryList());
    }
}
