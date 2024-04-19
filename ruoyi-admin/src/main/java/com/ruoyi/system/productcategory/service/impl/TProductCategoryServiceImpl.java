package com.ruoyi.system.productcategory.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.productcategory.mapper.TProductCategoryMapper;
import com.ruoyi.system.productcategory.domain.TProductCategory;
import com.ruoyi.system.productcategory.service.ITProductCategoryService;

/**
 * 商品和分类关联Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-19
 */
@Service
public class TProductCategoryServiceImpl implements ITProductCategoryService 
{
    @Autowired
    private TProductCategoryMapper tProductCategoryMapper;

    /**
     * 查询商品和分类关联
     * 
     * @param id 商品和分类关联主键
     * @return 商品和分类关联
     */
    @Override
    public TProductCategory selectTProductCategoryById(Long id)
    {
        return tProductCategoryMapper.selectTProductCategoryById(id);
    }

    /**
     * 查询商品和分类关联列表
     * 
     * @param tProductCategory 商品和分类关联
     * @return 商品和分类关联
     */
    @Override
    public List<TProductCategory> selectTProductCategoryList(TProductCategory tProductCategory)
    {
        return tProductCategoryMapper.selectTProductCategoryList(tProductCategory);
    }

    /**
     * 新增商品和分类关联
     * 
     * @param tProductCategory 商品和分类关联
     * @return 结果
     */
    @Override
    public int insertTProductCategory(TProductCategory tProductCategory)
    {
        tProductCategory.setCreateTime(DateUtils.getNowDate());
        return tProductCategoryMapper.insertTProductCategory(tProductCategory);
    }

    /**
     * 修改商品和分类关联
     * 
     * @param tProductCategory 商品和分类关联
     * @return 结果
     */
    @Override
    public int updateTProductCategory(TProductCategory tProductCategory)
    {
        return tProductCategoryMapper.updateTProductCategory(tProductCategory);
    }

    /**
     * 批量删除商品和分类关联
     * 
     * @param ids 需要删除的商品和分类关联主键
     * @return 结果
     */
    @Override
    public int deleteTProductCategoryByIds(Long[] ids)
    {
        return tProductCategoryMapper.deleteTProductCategoryByIds(ids);
    }

    /**
     * 删除商品和分类关联信息
     * 
     * @param id 商品和分类关联主键
     * @return 结果
     */
    @Override
    public int deleteTProductCategoryById(Long id)
    {
        return tProductCategoryMapper.deleteTProductCategoryById(id);
    }
}
