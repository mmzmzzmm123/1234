//package com.ruoyi.web.controller.product;
//
//import com.ruoyi.common.annotation.Anonymous;
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.system.service.IProductAttributeService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * 商品属性管理Controller
// */
//@Anonymous
//@Api(tags = "商品属性管理")
//@RestController
//@RequestMapping("/productAttribute")
//public class ProductAttributeController extends BaseController {
//    @Resource
//    private IProductAttributeService productAttributeService;
//
//    @ApiOperation("根据分类查询参数列表")
//    @GetMapping(value = "/list/{cid}")
//    public AjaxResult getList(@PathVariable Integer cid,
//                              @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
//                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        return success(productAttributeService.getList(cid, pageSize, pageNum));
//    }
//}
