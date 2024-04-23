package com.ruoyi.app.product;

import com.ruoyi.app.product.ao.UserProductAO;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.category.service.ITCategoryService;
import com.ruoyi.system.product.service.ITProductService;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;
import com.ruoyi.system.userproduct.service.ITUserProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/app/product")
public class AppProductController {

    @Resource
    private UserProductAO userProductAO;


    /**
     * 产品订阅
     */

    @PostMapping("/subscribe")
    public AjaxResult subscribe(Long productId, Long userId) {
        return userProductAO.addSubscribe(productId, userId);
    }


    /*
     * 查询订阅产品列表
     *
     * */
    @PostMapping("/querySubscribeProduct")
    public AjaxResult querySubscribeProduct(Long userId) {
        //todo-lx 模糊隐藏价
        return userProductAO.querySubscribeProduct(userId);
    }


}
