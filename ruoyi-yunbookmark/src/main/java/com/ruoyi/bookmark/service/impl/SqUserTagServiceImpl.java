package com.ruoyi.bookmark.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqUserTagMapper;
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.bookmark.service.ISqUserTagService;

/**
 * 标签管理Service业务层处理
 *
 * @author wang
 * @date 2020-09-04
 */
@Service
public class SqUserTagServiceImpl implements ISqUserTagService
{
    @Autowired
    private SqUserTagMapper sqUserTagMapper;


    /**
     *通过用户userID查用户的所有标签
     *
     *
     */
    @Override
    public List<Map<String,Object>>selectSqUserTagByUserId(Long userId){
        return sqUserTagMapper.selectSqUserTagByUserId(userId);
    }



    /**
     * 查询标签管理
     *
     * @param id 标签管理ID
     * @return 标签管理
     */
    @Override
    public SqUserTag selectSqUserTagById(Long id)
    {
        return sqUserTagMapper.selectSqUserTagById(id);
    }

    /**
     * 查询标签管理列表
     *
     * @param sqUserTag 标签管理
     * @return 标签管理
     */
    @Override
    public List<SqUserTag> selectSqUserTagList(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.selectSqUserTagList(sqUserTag);
    }

    /**
     * 新增标签管理
     *
     * @param sqUserTag 标签管理
     * @return 结果
     */
    @Override
    public int insertSqUserTag(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.insertSqUserTag(sqUserTag);
    }

    /**
     * 修改标签管理
     *
     * @param sqUserTag 标签管理
     * @return 结果
     */
    @Override
    public int updateSqUserTag(SqUserTag sqUserTag)
    {
        return sqUserTagMapper.updateSqUserTag(sqUserTag);
    }

    /**
     * 批量删除标签管理
     *
     * @param ids 需要删除的标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqUserTagByIds(Long[] ids)
    {
        return sqUserTagMapper.deleteSqUserTagByIds(ids);
    }

    /**
     * 删除标签管理信息
     *
     * @param id 标签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqUserTagById(Long id)
    {
        return sqUserTagMapper.deleteSqUserTagById(id);
    }
}
