package com.ruoyi.sq.service.impl;

import com.ruoyi.sq.mapper.SqBookmarkMapper;
import com.ruoyi.sq.service.ISqBookmarkService;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.sq.domain.SqBookmark;

/**
 * 书签Service业务层处理
 * 
 * @author wanghao
 * @date 2020-07-26
 */
@Service
public class SqBookmarkServiceImpl implements ISqBookmarkService
{
    @Autowired
    private SqBookmarkMapper sqBookmarkMapper;

    /**
     * 查询书签
     * 
     * @param bookmarkId 书签ID
     * @return 书签
     */
    @Override
    public SqBookmark selectSqBookmarkById(Long bookmarkId)
    {
        return sqBookmarkMapper.selectSqBookmarkById(bookmarkId);
    }

    /**
     * 查询书签列表
     * 
     * @param sqBookmark 书签
     * @return 书签
     */
    @Override
    public List<SqBookmark> selectSqBookmarkList(SqBookmark sqBookmark)
    {
        return sqBookmarkMapper.selectSqBookmarkList(sqBookmark);
    }

    /**
     * 新增书签
     * 
     * @param sqBookmark 书签
     * @return 结果
     */
    @Override
    public int insertSqBookmark(SqBookmark sqBookmark)
    {
        sqBookmark.setCreateTime(DateUtils.getNowDate());
        return sqBookmarkMapper.insertSqBookmark(sqBookmark);
    }

    /**
     * 修改书签
     * 
     * @param sqBookmark 书签
     * @return 结果
     */
    @Override
    public int updateSqBookmark(SqBookmark sqBookmark)
    {
        return sqBookmarkMapper.updateSqBookmark(sqBookmark);
    }

    /**
     * 批量删除书签
     * 
     * @param bookmarkIds 需要删除的书签ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkByIds(Long[] bookmarkIds)
    {
        return sqBookmarkMapper.deleteSqBookmarkByIds(bookmarkIds);
    }

    /**
     * 删除书签信息
     * 
     * @param bookmarkId 书签ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkById(Long bookmarkId)
    {
        return sqBookmarkMapper.deleteSqBookmarkById(bookmarkId);
    }
}
