package com.ruoyi.system.category.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.category.mapper.TCategoryMapper;
import com.ruoyi.system.category.domain.TCategory;
import com.ruoyi.system.category.service.ITCategoryService;

/**
 * 商品分类Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-19
 */
@Service
public class TCategoryServiceImpl implements ITCategoryService
{
    @Autowired
    private TCategoryMapper tCategoryMapper;

    /**
     * 查询商品分类
     *
     * @param id 商品分类主键
     * @return 商品分类
     */
    @Override
    public TCategory selectTCategoryById(Long id)
    {
        return tCategoryMapper.selectTCategoryById(id);
    }

    /**
     * 查询商品分类列表
     *
     * @param tCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<TCategory> selectTCategoryList(TCategory tCategory)
    {
        return tCategoryMapper.selectTCategoryList(tCategory);
    }

    @Override
    public List<TCategory> selectTCategoryListByIds(List<Long> ids) {
        return tCategoryMapper.selectTCategoryListByIds(ids);
    }

    /**
     * 新增商品分类
     *
     * @param tCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertTCategory(TCategory tCategory)
    {
        tCategory.setCreateTime(DateUtils.getNowDate());
        return tCategoryMapper.insertTCategory(tCategory);
    }

    /**
     * 修改商品分类
     *
     * @param tCategory 商品分类
     * @return 结果
     */
    @Override
    public int updateTCategory(TCategory tCategory)
    {
        tCategory.setUpdateTime(DateUtils.getNowDate());
        return tCategoryMapper.updateTCategory(tCategory);
    }

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteTCategoryByIds(Long[] ids)
    {
        return tCategoryMapper.deleteTCategoryByIds(ids);
    }

    /**
     * 删除商品分类信息
     *
     * @param id 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteTCategoryById(Long id)
    {
        return tCategoryMapper.deleteTCategoryById(id);
    }
}
