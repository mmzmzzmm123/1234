package com.ruoyi.app.product;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.app.product.ao.UserProductAO;
import com.ruoyi.app.product.vo.MobileProductVO;
import com.ruoyi.app.product.vo.ProductQuery;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.price.service.ITPriceService;
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.service.ITProductService;
import com.ruoyi.system.service.ISysUserService;
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
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ITProductService productService;


    /**
     * 产品订阅
     */
    @ApiOperation(value = "订阅产品",notes = "订阅产品")
    @PostMapping("/subscribe")
    public AjaxResult subscribe(@ApiParam("商品id")@RequestParam("productId") Long productId,@RequestParam("userId") @ApiParam("用户id") Long userId) {
        return userProductAO.addSubscribe(productId, userId);
    }


    /*
     * 查询订阅产品列表
     *
     * */
    @ApiOperation(value = "查询用户产品订阅列表",notes = "查询用户产品订阅列表")
    @PostMapping("/querySubscribeProduct")
    public R<List<MobileProductVO>> querySubscribeProduct(@ApiParam("用户id")@RequestParam("userId")Long userId) {
        return userProductAO.querySubscribeProduct(userId);
    }
    /*
    * 输出产品价格、涨幅、回收价格
    *
    * */
    @ApiOperation(value = "查询首页产品列表",notes = "查询首页产品列表")
    @GetMapping("/queryProductPriceList")
    public R<List<MobileProductVO>> queryProductPriceList(@RequestBody ProductQuery productQuery){
        return userProductAO.queryProductPriceList(productQuery);
    }

    @ApiOperation(value = "查询产品隐藏价格",notes = "查询产品隐藏价格")
    @GetMapping("/queryHidePrice")
    public R<MobileProductVO> queryHidePrice(@RequestParam("userId") Long userId,@RequestParam("productId") Long productId){
        if (ObjectUtil.hasNull(userId,productId)){
            return R.fail("查询失败，参数缺失");
        }
        MobileProductVO vo = new MobileProductVO();
        // 验证用户是否满足查看隐藏价格条件
        SysUser sysUser = sysUserService.selectUserById(userId);
        if (ObjectUtil.isNotNull(sysUser)){
            // 返回隐藏价格
            if (sysUser.getVip() > 0){
                TProduct tProduct = productService.selectTProductById(productId);
                BeanUtil.copyProperties(tProduct,vo);
            }else {
                return R.fail("该用户不是vip");
            }
        }else {
            return R.fail("用户不存在");
        }
        return R.ok(vo);
    }

}
