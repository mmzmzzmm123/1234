package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.BatchUpdateProductDTO;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.domain.dto.UpdateProductDTO;
import com.ruoyi.system.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : zengyi
 */
@Api(tags = "后台商品管理")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Resource
    private IProductService productService;

    @ApiOperation("商品列表")
    @PostMapping(value = "/list")
    public R<Page<ProductDTO>> getPage(@Validated @RequestBody ProductQueryParamDTO productQueryParam) {
        return R.ok(productService.getPage(productQueryParam));
    }

    @ApiOperation("添加商品")
    @PostMapping(value = "/create")
    public R<Boolean> create(@Validated @RequestBody ProductDTO productDTO) {
        productDTO.setOperatorUser(getUsername());
        productDTO.setOperatorUserId(getUserId());
        return R.ok(productService.create(productDTO));
    }

    @ApiOperation("根据商品id获取商品编辑信息")
    @GetMapping(value = "/detail/{id}")
    public R<ProductDTO> detail(@PathVariable Long id) {
        return R.ok(productService.detail(id));
    }

    @ApiOperation("更新商品信息")
    @PostMapping(value = "/update")
    public R<Boolean> update(@Validated @RequestBody ProductDTO productDTO) {
        if (ObjectUtils.isEmpty(productDTO.getProductId())) {
            return R.ok(Boolean.FALSE);
        }
        return R.ok(productService.update(productDTO));
    }

    @ApiOperation("更新商品价格")
    @PostMapping(value = "/updatePrice")
    public R<Boolean> updatePrice(@RequestBody UpdateProductDTO productDTO) {
        return R.ok(productService.handleUpdatePrice(productDTO));
    }

    @ApiOperation("批量更新商品状态")
    @PostMapping(value = "/updateStatus")
    public R<Boolean> updateStatus(@RequestBody BatchUpdateProductDTO productDTO) {
        return R.ok(productService.batchUpdateStatus(productDTO));
    }

    @ApiOperation("批量更新商品显示状态")
    @PostMapping(value = "/updateShowStatus")
    public R<Boolean> updateShowStatus(@RequestBody BatchUpdateProductDTO productDTO) {
        return R.ok(productService.batchUpdateShowStatus(productDTO));
    }

    @ApiOperation("批量删除商品")
    @PostMapping(value = "/deleteProducts")
    public R<Boolean> deleteProducts(@RequestBody BatchUpdateProductDTO productDTO) {
        return R.ok(productService.batchDeleteProducts(productDTO));
    }
}
