//package com.ruoyi.web.controller.business;
//
//import com.ruoyi.common.core.controller.BaseController;
//import com.ruoyi.common.core.domain.AjaxResult;
//import com.ruoyi.common.core.domain.app.ProductRequest;
//import com.ruoyi.system.service.IPortalProductService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@Api("前台商品")
//@RestController
//@RequestMapping("portal/product")
//public class ProductController extends BaseController {
//    @Resource
//    private IPortalProductService productService;
//
//    @ApiOperation("列表")
//    @PostMapping("list")
//    public AjaxResult list(@RequestBody ProductRequest dto) {
//        return AjaxResult.success(productService.getList(dto));
//    }
//}
