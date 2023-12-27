package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.system.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : zengyi
 */
@Anonymous
@Api(tags = "前台商品")
@RestController
@RequestMapping("/homeProduct")
public class HomeProductController extends BaseController {
    @Resource
    private IProductService productService;

    @ApiOperation("商品首页")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody ProductRequest dto) {
        return AjaxResult.success(productService.getNormalList(dto));
    }

    @ApiOperation("商品详情")
    @GetMapping("/detail/{skuId}")
    public AjaxResult getProductDetailBySkuId(@PathVariable Long skuId) {
        return AjaxResult.success(productService.getProductDetailBySkuId(skuId));
    }
}