package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "后台商品管理")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Resource
    private IProductService productService;

    @ApiOperation("商品列表")
    @PostMapping(value = "/list")
    public AjaxResult getPage(@RequestBody ProductQueryParamDTO productQueryParam) {
        return success(productService.getPage(productQueryParam));
    }

    @ApiOperation("添加商品")
    @PostMapping(value = "/create")
    public AjaxResult create(@RequestBody ProductDTO productDTO) {
        return success(productService.create(productDTO));
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(productService.detail(id));
    }

    @ApiOperation("更新商品")
    @PostMapping(value = "/update")
    public AjaxResult update(@RequestBody ProductDTO productDTO) {
        return success(productService.update(productDTO));
    }
}
