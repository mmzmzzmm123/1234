package com.ruoyi.system.userproduct.service;

import java.util.List;

import com.ruoyi.system.product.domain.TProduct;
import com.ruoyi.system.userproduct.domain.TUserProduct;
import org.apache.ibatis.annotations.Param;

/**
 * 用户订阅产品Service接口
 *
 * @author ruoyi
 * @date 2024-04-23
 */
public interface ITUserProductService
{
    /**
     * 查询用户订阅产品
     *
     * @param id 用户订阅产品主键
     * @return 用户订阅产品
     */
    public TUserProduct selectTUserProductById(Long id);

    /**
     * 查询用户订阅产品列表
     *
     * @param tUserProduct 用户订阅产品
     * @return 用户订阅产品集合
     */
    public List<TUserProduct> selectTUserProductList(TUserProduct tUserProduct);

    /**
     * 新增用户订阅产品
     *
     * @param tUserProduct 用户订阅产品
     * @return 结果
     */
    public int insertTUserProduct(TUserProduct tUserProduct);

    /**
     * 修改用户订阅产品
     *
     * @param tUserProduct 用户订阅产品
     * @return 结果
     */
    public int updateTUserProduct(TUserProduct tUserProduct);

    /**
     * 批量删除用户订阅产品
     *
     * @param ids 需要删除的用户订阅产品主键集合
     * @return 结果
     */
    public int deleteTUserProductByIds(Long[] ids);

    /**
     * 删除用户订阅产品信息
     *
     * @param id 用户订阅产品主键
     * @return 结果
     */
    public int deleteTUserProductById(Long id);

    List<TProduct> queryTProductsByUserId(Long userId);
}
