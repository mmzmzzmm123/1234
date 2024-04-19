package com.ruoyi.system.category.mapper;

import java.util.List;
import com.ruoyi.system.category.domain.TCategory;

/**
 * 商品分类Mapper接口
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public interface TCategoryMapper
{
    /**
     * 查询商品分类
     *
     * @param id 商品分类主键
     * @return 商品分类
     */
    public TCategory selectTCategoryById(Long id);

    /**
     * 查询商品分类列表
     *
     * @param tCategory 商品分类
     * @return 商品分类集合
     */
    public List<TCategory> selectTCategoryList(TCategory tCategory);

    /**
     * 新增商品分类
     *
     * @param tCategory 商品分类
     * @return 结果
     */
    public int insertTCategory(TCategory tCategory);

    /**
     * 修改商品分类
     *
     * @param tCategory 商品分类
     * @return 结果
     */
    public int updateTCategory(TCategory tCategory);

    /**
     * 删除商品分类
     *
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteTCategoryById(Long id);

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTCategoryByIds(Long[] ids);

    public List<TCategory> selectTCategoryListByIds(List<Long> ids);
}
