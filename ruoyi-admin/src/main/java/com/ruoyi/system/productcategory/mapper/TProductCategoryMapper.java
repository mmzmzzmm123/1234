package com.ruoyi.system.productcategory.mapper;

import java.util.List;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import org.apache.ibatis.annotations.Param;

/**
 * 商品和分类关联Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public interface TProductCategoryMapper
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
    public int insertTProductCategoryBatch(@Param("tProductCategories") List<TProductCategory> tProductCategories);


    /**
     * 修改商品和分类关联
     *
     * @param tProductCategory 商品和分类关联
     * @return 结果
     */
    public int updateTProductCategory(TProductCategory tProductCategory);

    /**
     * 删除商品和分类关联
     *
     * @param id 商品和分类关联主键
     * @return 结果
     */
    public int deleteTProductCategoryById(Long id);

    /**
     * 批量删除商品和分类关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTProductCategoryByIds(Long[] ids);
}
