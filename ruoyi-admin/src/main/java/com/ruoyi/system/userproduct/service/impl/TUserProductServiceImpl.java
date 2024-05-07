package com.ruoyi.system.userproduct.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.product.domain.TProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.userproduct.mapper.TUserProductMapper;
import com.ruoyi.system.userproduct.domain.TUserProduct;
import com.ruoyi.system.userproduct.service.ITUserProductService;

/**
 * 用户订阅产品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-23
 */
@Service
public class TUserProductServiceImpl implements ITUserProductService
{
    @Autowired
    private TUserProductMapper tUserProductMapper;

    /**
     * 查询用户订阅产品
     *
     * @param id 用户订阅产品主键
     * @return 用户订阅产品
     */
    @Override
    public TUserProduct selectTUserProductById(Long id)
    {
        return tUserProductMapper.selectTUserProductById(id);
    }

    /**
     * 查询用户订阅产品列表
     *
     * @param tUserProduct 用户订阅产品
     * @return 用户订阅产品
     */
    @Override
    public List<TUserProduct> selectTUserProductList(TUserProduct tUserProduct)
    {
        return tUserProductMapper.selectTUserProductList(tUserProduct);
    }

    /**
     * 新增用户订阅产品
     *
     * @param tUserProduct 用户订阅产品
     * @return 结果
     */
    @Override
    public int insertTUserProduct(TUserProduct tUserProduct)
    {
        tUserProduct.setCreateTime(DateUtils.getNowDate());
        return tUserProductMapper.insertTUserProduct(tUserProduct);
    }

    /**
     * 修改用户订阅产品
     *
     * @param tUserProduct 用户订阅产品
     * @return 结果
     */
    @Override
    public int updateTUserProduct(TUserProduct tUserProduct)
    {
        return tUserProductMapper.updateTUserProduct(tUserProduct);
    }

    /**
     * 批量删除用户订阅产品
     *
     * @param ids 需要删除的用户订阅产品主键
     * @return 结果
     */
    @Override
    public int deleteTUserProductByIds(Long[] ids)
    {
        return tUserProductMapper.deleteTUserProductByIds(ids);
    }

    /**
     * 删除用户订阅产品信息
     *
     * @param id 用户订阅产品主键
     * @return 结果
     */
    @Override
    public int deleteTUserProductById(Long id)
    {
        return tUserProductMapper.deleteTUserProductById(id);
    }

    @Override
    public List<TProduct> queryTProductsByUserId(Long userId) {
        return tUserProductMapper.queryTProductsByUserId(userId);
    }

}
