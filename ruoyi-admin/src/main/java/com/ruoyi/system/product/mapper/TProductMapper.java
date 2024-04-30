package com.ruoyi.system.product.mapper;

import java.util.List;
import com.ruoyi.system.product.domain.TProduct;

/**
 * 商品Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-19
 */
public interface TProductMapper 
{
    /**
     * 查询商品
     * 
     * @param id 商品主键
     * @return 商品
     */
    public TProduct selectTProductById(Long id);

    /**
     * 查询商品列表
     * 
     * @param tProduct 商品
     * @return 商品集合
     */
    public List<TProduct> selectTProductList(TProduct tProduct);

    /**
     * 新增商品
     * 
     * @param tProduct 商品
     * @return 结果
     */
    public int insertTProduct(TProduct tProduct);

    /**
     * 修改商品
     * 
     * @param tProduct 商品
     * @return 结果
     */
    public int updateTProduct(TProduct tProduct);

    /**
     * 删除商品
     * 
     * @param id 商品主键
     * @return 结果
     */
    public int deleteTProductById(Long id);

    /**
     * 批量删除商品
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTProductByIds(Long[] ids);
}
