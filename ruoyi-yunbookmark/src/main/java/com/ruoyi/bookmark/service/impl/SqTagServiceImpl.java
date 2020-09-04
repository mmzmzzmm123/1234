package com.ruoyi.bookmark.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqTagMapper;
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.service.ISqTagService;

/**
 * 书签_标签Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-04
 */
@Service
public class SqTagServiceImpl implements ISqTagService 
{
    @Autowired
    private SqTagMapper sqTagMapper;

    /**
     * 查询书签_标签
     * 
     * @param id 书签_标签ID
     * @return 书签_标签
     */
    @Override
    public SqTag selectSqTagById(Long id)
    {
        return sqTagMapper.selectSqTagById(id);
    }

    /**
     * 查询书签_标签列表
     * 
     * @param sqTag 书签_标签
     * @return 书签_标签
     */
    @Override
    public List<SqTag> selectSqTagList(SqTag sqTag)
    {
        return sqTagMapper.selectSqTagList(sqTag);
    }

    /**
     * 新增书签_标签
     * 
     * @param sqTag 书签_标签
     * @return 结果
     */
    @Override
    public int insertSqTag(SqTag sqTag)
    {
        sqTag.setCreateTime(DateUtils.getNowDate());
        return sqTagMapper.insertSqTag(sqTag);
    }

    /**
     * 修改书签_标签
     * 
     * @param sqTag 书签_标签
     * @return 结果
     */
    @Override
    public int updateSqTag(SqTag sqTag)
    {
        return sqTagMapper.updateSqTag(sqTag);
    }

    /**
     * 批量删除书签_标签
     * 
     * @param ids 需要删除的书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqTagByIds(Long[] ids)
    {
        return sqTagMapper.deleteSqTagByIds(ids);
    }

    /**
     * 删除书签_标签信息
     * 
     * @param id 书签_标签ID
     * @return 结果
     */
    @Override
    public int deleteSqTagById(Long id)
    {
        return sqTagMapper.deleteSqTagById(id);
    }
}
