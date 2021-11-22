package com.ruoyi.bookmark.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBackupBookmarkMapper;
import com.ruoyi.bookmark.domain.SqBackupBookmark;
import com.ruoyi.bookmark.service.ISqBackupBookmarkService;

/**
 * 备份书签Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-22
 */
@Service
public class SqBackupBookmarkServiceImpl implements ISqBackupBookmarkService 
{
    @Autowired
    private SqBackupBookmarkMapper sqBackupBookmarkMapper;

    /**
     * 查询备份书签
     * 
     * @param id 备份书签ID
     * @return 备份书签
     */
    @Override
    public SqBackupBookmark selectSqBackupBookmarkById(Long id)
    {
        return sqBackupBookmarkMapper.selectSqBackupBookmarkById(id);
    }

    /**
     * 查询备份书签列表
     * 
     * @param sqBackupBookmark 备份书签
     * @return 备份书签
     */
    @Override
    public List<SqBackupBookmark> selectSqBackupBookmarkList(SqBackupBookmark sqBackupBookmark)
    {
        return sqBackupBookmarkMapper.selectSqBackupBookmarkList(sqBackupBookmark);
    }

    /**
     * 新增备份书签
     * 
     * @param sqBackupBookmark 备份书签
     * @return 结果
     */
    @Override
    public int insertSqBackupBookmark(SqBackupBookmark sqBackupBookmark)
    {
        sqBackupBookmark.setCreateTime(DateUtils.getNowDate());
        return sqBackupBookmarkMapper.insertSqBackupBookmark(sqBackupBookmark);
    }

    /**
     * 修改备份书签
     * 
     * @param sqBackupBookmark 备份书签
     * @return 结果
     */
    @Override
    public int updateSqBackupBookmark(SqBackupBookmark sqBackupBookmark)
    {
        sqBackupBookmark.setUpdateTime(DateUtils.getNowDate());
        return sqBackupBookmarkMapper.updateSqBackupBookmark(sqBackupBookmark);
    }

    /**
     * 批量删除备份书签
     * 
     * @param ids 需要删除的备份书签ID
     * @return 结果
     */
    @Override
    public int deleteSqBackupBookmarkByIds(Long[] ids)
    {
        return sqBackupBookmarkMapper.deleteSqBackupBookmarkByIds(ids);
    }

    /**
     * 删除备份书签信息
     * 
     * @param id 备份书签ID
     * @return 结果
     */
    @Override
    public int deleteSqBackupBookmarkById(Long id)
    {
        return sqBackupBookmarkMapper.deleteSqBackupBookmarkById(id);
    }
}
