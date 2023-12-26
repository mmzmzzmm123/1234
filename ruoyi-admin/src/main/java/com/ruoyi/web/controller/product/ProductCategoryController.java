//package com.ruoyi.web.controller.product;
//
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.system.service.IProductCategoryService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@Api(tags = "商品分类管理")
//@RestController
//@RequestMapping("/productCategory")
//public class ProductCategoryController extends BaseController {
//    @Resource
//    private IProductCategoryService productCategoryService;
//
//    @ApiOperation("查询所有一级分类及子分类")
//    @GetMapping( "/list")
//    public AjaxResult selectAll() {
//        return AjaxResult.success(productCategoryService.selectAll());
//    }
//}
