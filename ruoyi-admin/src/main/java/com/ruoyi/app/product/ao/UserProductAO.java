package com.ruoyi.app.product.ao;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.app.product.vo.MobileProductVO;
import com.ruoyi.app.product.vo.ProductQuery;
import com.ruoyi.app.product.vo.TpriceVO;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.QueryTypeEnum;
import com.ruoyi.system.category.service.ITCategoryService;
import com.ruoyi.system.price.domain.TPrice;
import com.ruoyi.system.price.service.ITPriceService;
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.service.ITProductService;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.userproduct.domain.TUserProduct;
import com.ruoyi.system.userproduct.service.ITUserProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.management.Query;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 用户产品ao
 *
 * @author Guard Baby
 * @date 2024/04/25
 */
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
    @Resource
    private ITPriceService priceService;


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
            List<MobileProductVO> voList = BeanUtil.copyToList(products, MobileProductVO.class);
            for (MobileProductVO vo : voList) {
                vo.setHidePrice("**");
                vo.settCategoryList(categoryService.selectTCategoryListByProductId(vo.getId()));
                //查询价格变动
                TPrice price = priceService.queryLastTprice(vo.getId());
                if (ObjectUtil.isNotNull(price)) {
                    TpriceVO tpriceVO = new TpriceVO();
                    BeanUtil.copyProperties(price,tpriceVO);
                    tpriceVO.setHidePrice("**");
                    vo.settPrice(tpriceVO);
                    vo.setSubPrice(price.getPrice().subtract(vo.getPrice()));
                }
            }
            return R.ok(voList);
        }
        return R.ok();
    }

    public R<List<MobileProductVO>> queryProductPriceList(ProductQuery productQuery) {
        //根据某种排序方式返回产品列表
        List<TProduct> productList = new ArrayList<>();
        QueryTypeEnum queryTypeEnum = productQuery.getQueryType();
        Long categoryId = productQuery.getCategoryId();
        if (ObjectUtil.isNotNull(categoryId)) {
            productList = productCategoryService.queryProductListByCategoryId(categoryId);
        } else {
            productList = productService.selectTProductList(null);
        }
        return R.ok(sort(productList, queryTypeEnum));
    }

    List<MobileProductVO> sort(List<TProduct> products, QueryTypeEnum queryType) {
        List<MobileProductVO> voList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date today = DateUtil.endOfDay(new Date());
        switch (queryType) {
            case HALF_YEAR:
                calendar.add(Calendar.MONTH, -6);
                break;
            case SEASON:
                calendar.add(Calendar.MONTH, -3);
                break;
            default:
                calendar.add(Calendar.DAY_OF_YEAR, -30);
                break;
        }
        Date toLocateTime = calendar.getTime();
        for (TProduct tProduct : products) {
            MobileProductVO vo = new MobileProductVO();
            BeanUtil.copyProperties(tProduct, vo);
            TPrice price = priceService.queryLastTprice(tProduct.getId(), toLocateTime);
            if (ObjectUtil.isNotNull(price)) {
                vo.setSubPrice(price.getPrice().subtract(tProduct.getPrice()));
                TpriceVO tpriceVO = new TpriceVO();
                BeanUtil.copyProperties(price,tpriceVO);
                tpriceVO.setHidePrice("**");
                vo.settPrice(tpriceVO);
            } else {
                vo.setSubPrice(BigDecimal.ZERO);
            }
            vo.setHidePrice("**");
            voList.add(vo);
        }
        return voList.stream().sorted(Comparator.comparing(MobileProductVO::getSubPrice).reversed()).collect(Collectors.toList());
    }

}
