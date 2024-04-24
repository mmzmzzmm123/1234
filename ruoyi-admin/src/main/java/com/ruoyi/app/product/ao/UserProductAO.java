package com.ruoyi.app.product.ao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.app.product.vo.MobileProductVO;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.category.service.ITCategoryService;
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.service.ITProductService;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.userproduct.domain.TUserProduct;
import com.ruoyi.system.userproduct.service.ITUserProductService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("userProductAO")
public class UserProductAO {
    @Resource
    private ITProductService productService;
    @Resource
    private ITCategoryService categoryService;
    @Resource
    private ITProductCategoryService productCategoryService;
    @Resource
    private ITUserProductService userProductService;
    @Resource
    private ISysUserService sysUserService;


    /*
     * 增加用户订阅
     *
     * */
    public AjaxResult addSubscribe(Long productId, Long userId) {
        if (ObjectUtil.hasNull(productId, userId)) {
            return AjaxResult.error("参数错误");
        }
        TProduct tProduct = productService.selectTProductById(productId);
        SysUser sysUser = sysUserService.selectUserById(userId);
        if (ObjectUtil.hasNull(tProduct, sysUser)) {
            return AjaxResult.error("未找到有效的产品或用户~");
        }
        TUserProduct tUserProduct = new TUserProduct();
        tUserProduct.setProductId(productId);
        tUserProduct.setUserId(userId);
        tUserProduct.setCreateTime(new Date());
        userProductService.insertTUserProduct(tUserProduct);
        return AjaxResult.success();
    }

    public R<List<MobileProductVO>> querySubscribeProduct(Long userId) {
        if (ObjectUtil.hasNull(userId)) {
            return R.fail("参数错误");
        }
        SysUser sysUser = sysUserService.selectUserById(userId);
        if (ObjectUtil.hasNull(sysUser)) {
            return R.fail("未找到有效用户~");
        }
        TUserProduct tUserProductQuery = new TUserProduct();
        tUserProductQuery.setUserId(userId);
        List<TUserProduct> tUserProducts = userProductService.selectTUserProductList(tUserProductQuery);
        if (CollUtil.isNotEmpty(tUserProducts)) {
            List<TProduct> products = userProductService.queryTProductsByUserId(userId);
            // 模糊隐藏价
            List<MobileProductVO> voList = BeanUtil.copyToList(products,MobileProductVO.class);
            for (MobileProductVO vo : voList){
                vo.setHidePrice("**");
            }
            return R.ok(voList);
        }
        return R.ok();
    }
    public R<List<MobileProductVO>> queryProductPriceList(){

        return R.ok();
    }


}
