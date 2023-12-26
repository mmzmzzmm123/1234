//package com.ruoyi.web.controller.product;
//
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.system.service.IProductAttributeCategoryService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
//@Api(tags = "商品属性分类管理")
//@RestController
//@RequestMapping("/productAttribute/category")
//public class ProductAttributeCategoryController extends BaseController {
//    @Resource
//    private IProductAttributeCategoryService productAttributeCategoryService;
//
//    @ApiOperation("分页获取所有商品属性分类")
//    @GetMapping( "/list")
//    public AjaxResult getList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "20") Integer pageSize) {
//        return AjaxResult.success(productAttributeCategoryService.getList(pageNum, pageSize));
//    }
//}
