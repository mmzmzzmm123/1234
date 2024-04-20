package com.ruoyi.system.productcategory.service;

import java.util.List;
import com.ruoyi.system.productcategory.domain.TProductCategory;

/**
 * 商品和分类关联Service接口
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public interface ITProductCategoryService
{
    /**
     * 查询商品和分类关联
     *
     * @param id 商品和分类关联主键
     * @return 商品和分类关联
     */
    public TProductCategory selectTProductCategoryById(Long id);

    /**
     * 查询商品和分类关联列表
     *
     * @param tProductCategory 商品和分类关联
     * @return 商品和分类关联集合
     */
    public List<TProductCategory> selectTProductCategoryList(TProductCategory tProductCategory);

    /**
     * 新增商品和分类关联
     *
     * @param tProductCategory 商品和分类关联
     * @return 结果
     */
    public int insertTProductCategory(TProductCategory tProductCategory);

    /**
     * 修改商品和分类关联
     *
     * @param tProductCategory 商品和分类关联
     * @return 结果
     */
    public int updateTProductCategory(TProductCategory tProductCategory);

    /**
     * 批量删除商品和分类关联
     *
     * @param ids 需要删除的商品和分类关联主键集合
     * @return 结果
     */
    public int deleteTProductCategoryByIds(Long[] ids);

    /**
     * 删除商品和分类关联信息
     *
     * @param id 商品和分类关联主键
     * @return 结果
     */
    public int deleteTProductCategoryById(Long id);

    public int inertBatch(List<TProductCategory> list);
}
