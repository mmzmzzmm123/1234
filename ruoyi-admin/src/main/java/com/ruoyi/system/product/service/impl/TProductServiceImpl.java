package com.ruoyi.system.product.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.category.domain.TCategory;
import com.ruoyi.system.category.mapper.TCategoryMapper;
import com.ruoyi.system.price.domain.TPrice;
import com.ruoyi.system.price.mapper.TPriceMapper;
import com.ruoyi.system.product.domain.vo.TProductVO;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.ruoyi.system.product.mapper.TProductMapper;
import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.product.service.ITProductService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 商品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@Service
public class TProductServiceImpl implements ITProductService {
    @Resource
    private TProductMapper tProductMapper;
    @Resource
    private TProductCategoryMapper productCategoryMapper;
    @Resource
    private TCategoryMapper categoryMapper;
    @Resource
    private TPriceMapper priceMapper;

    /**
     * 查询商品
     *
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public TProduct selectTProductById(Long id) {
        return tProductMapper.selectTProductById(id);
    }

    @Override
    public TProductVO selectTProducVotById(Long id) {
        TProductVO tProductVO = new TProductVO();
        TProduct tProduct = tProductMapper.selectTProductById(id);
        if (Objects.nonNull(tProduct)) {
            BeanUtil.copyProperties(tProduct, tProductVO);
            TProductCategory tProductCategoryQuery = new TProductCategory();
            tProductCategoryQuery.setProductId(id);
            List<TProductCategory> tProductCategories = productCategoryMapper.selectTProductCategoryList(tProductCategoryQuery);
            if (CollUtil.isNotEmpty(tProductCategories)) {
                tProductVO.setCategoryList(tProductCategories.stream().map(TProductCategory::getCategoryId).collect(Collectors.toList()));
            }
            return tProductVO;

        }
        return null;
    }

    /**
     * 查询商品列表
     *
     * @param tProduct 商品
     * @return 商品
     */
    @Override
    public List<TProduct> selectTProductList(TProduct tProduct) {
        return tProductMapper.selectTProductList(tProduct);
    }

    @Override
    public List<TProductVO> selectTProductVOList(TProduct tProduct) {
        List<TProductVO> voList = new ArrayList<>();
        List<TProduct> tProducts = tProductMapper.selectTProductList(tProduct);
        if (CollUtil.isNotEmpty(tProducts)) {
            for (TProduct product : tProducts) {

            }
        }
        return null;
    }

    /**
     * 新增商品
     *
     * @param tProduct 商品
     * @return 结果
     */
    @Override
    public int insertTProduct(TProduct tProduct) {
        tProduct.setCreateTime(DateUtils.getNowDate());
        return tProductMapper.insertTProduct(tProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTProductVO(TProductVO tProductVO) {
        TProduct tProduct = new TProduct();
        BeanUtil.copyProperties(tProductVO, tProduct);
        int result = tProductMapper.insertTProduct(tProduct);
        TPrice tPrice = new TPrice();
        tPrice.setProductId(tProduct.getId());
        tPrice.setPrice(tProduct.getPrice());
        tPrice.setCreateTime(new Date());
        priceMapper.insertTPrice(tPrice);
        if (CollUtil.isNotEmpty(tProductVO.getCategoryList())) {
            List<TProductCategory> tProductCategories = new ArrayList<>();
            for (Long categoryId : tProductVO.getCategoryList()) {
                TProductCategory tProductCategory = new TProductCategory();
                tProductCategory.setCategoryId(categoryId);
                tProductCategory.setProductId(tProduct.getId());
                tProductCategory.setCreateTime(new Date());
                tProductCategories.add(tProductCategory);
            }
            productCategoryMapper.insertTProductCategoryBatch(tProductCategories);
        }
        return result;
    }

    /**
     * 修改商品
     *
     * @param tProduct 商品
     * @return 结果
     */
    @Override
    public int updateTProduct(TProduct tProduct) {
        tProduct.setUpdateTime(DateUtils.getNowDate());
        return tProductMapper.updateTProduct(tProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTProductVO(TProductVO tProductVO) {
        TProduct tProduct = new TProduct();
        BeanUtil.copyProperties(tProductVO, tProduct);
        tProduct.setUpdateTime(DateUtils.getNowDate());
        int result = tProductMapper.updateTProduct(tProduct);
        //todo-lx 记录价格变动日志
        TPrice tPrice = new TPrice();
        tPrice.setProductId(tProduct.getId());
        tPrice.setPrice(tProduct.getPrice());
        tPrice.setCreateTime(new Date());
        priceMapper.insertTPrice(tPrice);
        productCategoryMapper.deleteByTProductId(tProductVO.getId());
        if (CollUtil.isNotEmpty(tProductVO.getCategoryList())) {
            List<TProductCategory> tProductCategories = new ArrayList<>();
            for (Long categoryId : tProductVO.getCategoryList()) {
                TProductCategory tProductCategory = new TProductCategory();
                tProductCategory.setProductId(tProductVO.getId());
                tProductCategory.setCategoryId(categoryId);
                tProductCategory.setCreateTime(new Date());
                tProductCategories.add(tProductCategory);
            }
            productCategoryMapper.insertTProductCategoryBatch(tProductCategories);
        }
        return result;
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteTProductByIds(Long[] ids) {
        return tProductMapper.deleteTProductByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteTProductById(Long id) {
        return tProductMapper.deleteTProductById(id);
    }
}
