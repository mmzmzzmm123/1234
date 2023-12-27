package com.ruoyi.web.controller.product;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.dto.BatchUpdateProductDTO;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.domain.dto.UpdateProductDTO;
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
        productDTO.setOperatorUser(getUsername());
        return success(productService.create(productDTO));
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult detail(@PathVariable Long id) {
        return success(productService.detail(id));
    }

    @ApiOperation("更新商品信息")
    @PostMapping(value = "/update")
    public AjaxResult update(@RequestBody ProductDTO productDTO) {
        return success(productService.update(productDTO));
    }

    @ApiOperation("更新商品价格")
    @PostMapping(value = "/updatePrice")
    public AjaxResult updatePrice(@RequestBody UpdateProductDTO productDTO) {
        return success(productService.updatePrice(productDTO));
    }

    @ApiOperation("批量更新商品状态")
    @PostMapping(value = "/updateStatus")
    public AjaxResult updateStatus(@RequestBody BatchUpdateProductDTO productDTO) {
        return success(productService.updateStatus(productDTO));
    }

    @ApiOperation("批量更新商品显示状态")
    @PostMapping(value = "/updateShowStatus")
    public AjaxResult updateShowStatus(@RequestBody BatchUpdateProductDTO productDTO) {
        return success(productService.updateShowStatus(productDTO));
    }

    @ApiOperation("批量删除商品")
    @PostMapping(value = "/deleteProducts")
    public AjaxResult deleteProducts(@RequestBody BatchUpdateProductDTO productDTO) {
        return success(productService.deleteProducts(productDTO));
    }
}
