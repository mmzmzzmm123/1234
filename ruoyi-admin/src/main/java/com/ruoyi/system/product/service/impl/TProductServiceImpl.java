package com.ruoyi.system.product.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.product.domain.vo.TProductVO;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TProductServiceImpl implements ITProductService
{
    @Autowired
    private TProductMapper tProductMapper;
    @Resource
    private TProductCategoryMapper productCategoryMapper ;

    /**
     * 查询商品
     *
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public TProduct selectTProductById(Long id)
    {
        return tProductMapper.selectTProductById(id);
    }

    /**
     * 查询商品列表
     *
     * @param tProduct 商品
     * @return 商品
     */
    @Override
    public List<TProduct> selectTProductList(TProduct tProduct)
    {
        return tProductMapper.selectTProductList(tProduct);
    }

    /**
     * 新增商品
     *
     * @param tProduct 商品
     * @return 结果
     */
    @Override
    public int insertTProduct(TProduct tProduct)
    {
        tProduct.setCreateTime(DateUtils.getNowDate());
        return tProductMapper.insertTProduct(tProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTProductVO(TProductVO tProductVO) {
        TProduct tProduct = new TProduct();
        BeanUtil.copyProperties(tProductVO,tProduct);
        tProductMapper.insertTProduct(tProduct);
        if (CollUtil.isNotEmpty(tProductVO.getCategoryList())){
//            productCategoryMapper.
            //todo 构建批量保存方法
        }
        return 0;
    }

    /**
     * 修改商品
     *
     * @param tProduct 商品
     * @return 结果
     */
    @Override
    public int updateTProduct(TProduct tProduct)
    {
        tProduct.setUpdateTime(DateUtils.getNowDate());
        return tProductMapper.updateTProduct(tProduct);
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteTProductByIds(Long[] ids)
    {
        return tProductMapper.deleteTProductByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteTProductById(Long id)
    {
        return tProductMapper.deleteTProductById(id);
    }
}
