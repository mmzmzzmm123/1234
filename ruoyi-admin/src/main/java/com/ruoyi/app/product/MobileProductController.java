package com.ruoyi.app.product;

import com.ruoyi.app.product.ao.UserProductAO;
import com.ruoyi.app.product.vo.MobileProductVO;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 移动端接口
 *
 * @author Guard Baby
 * @date 2024/04/24
 */
@Api("移动端商品接口")
@RestController
@RequestMapping("/app/product")
public class MobileProductController {

    @Resource
    private UserProductAO userProductAO;


    /**
     * 产品订阅
     */
    @ApiOperation(value = "产品订阅接口",notes = "产品订阅接口")
    @PostMapping("/subscribe")
    public AjaxResult subscribe(@ApiParam("商品id")@RequestParam("productId") Long productId,@RequestParam("userId") @ApiParam("用户id") Long userId) {
        return userProductAO.addSubscribe(productId, userId);
    }


    /*
     * 查询订阅产品列表
     *
     * */
    @ApiOperation(value = "产品订阅查询接口",notes = "产品订阅查询接口")
    @PostMapping("/querySubscribeProduct")
    public R<List<MobileProductVO>> querySubscribeProduct(@ApiParam("用户id")@RequestParam("userId")Long userId) {
        return userProductAO.querySubscribeProduct(userId);
    }
    /*
    * 输出产品价格、涨幅、回收价格
    *
    * */
    @ApiOperation(value = "首页产品列表接口",notes = "首页产品列表接口")
    @GetMapping("/queryProductPriceList")
    public R<List<MobileProductVO>> queryProductPriceList(){
        return userProductAO.queryProductPriceList();
    }


}
