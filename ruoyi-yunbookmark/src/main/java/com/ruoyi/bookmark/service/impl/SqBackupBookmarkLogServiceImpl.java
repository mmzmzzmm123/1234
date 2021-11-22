package com.ruoyi.bookmark.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBackupBookmarkLogMapper;
import com.ruoyi.bookmark.domain.SqBackupBookmarkLog;
import com.ruoyi.bookmark.service.ISqBackupBookmarkLogService;

/**
 * 备份记录表Service业务层处理
 * 
 * @author wanghao
 * @date 2021-11-22
 */
@Service
public class SqBackupBookmarkLogServiceImpl implements ISqBackupBookmarkLogService 
{
    @Autowired
    private SqBackupBookmarkLogMapper sqBackupBookmarkLogMapper;

    /**
     * 查询备份记录表
     * 
     * @param id 备份记录表ID
     * @return 备份记录表
     */
    @Override
    public SqBackupBookmarkLog selectSqBackupBookmarkLogById(Long id)
    {
        return sqBackupBookmarkLogMapper.selectSqBackupBookmarkLogById(id);
    }

    /**
     * 查询备份记录表列表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 备份记录表
     */
    @Override
    public List<SqBackupBookmarkLog> selectSqBackupBookmarkLogList(SqBackupBookmarkLog sqBackupBookmarkLog)
    {
        return sqBackupBookmarkLogMapper.selectSqBackupBookmarkLogList(sqBackupBookmarkLog);
    }

    /**
     * 新增备份记录表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 结果
     */
    @Override
    public int insertSqBackupBookmarkLog(SqBackupBookmarkLog sqBackupBookmarkLog)
    {
        sqBackupBookmarkLog.setCreateTime(DateUtils.getNowDate());
        return sqBackupBookmarkLogMapper.insertSqBackupBookmarkLog(sqBackupBookmarkLog);
    }

    /**
     * 修改备份记录表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 结果
     */
    @Override
    public int updateSqBackupBookmarkLog(SqBackupBookmarkLog sqBackupBookmarkLog)
    {
        return sqBackupBookmarkLogMapper.updateSqBackupBookmarkLog(sqBackupBookmarkLog);
    }

    /**
     * 批量删除备份记录表
     * 
     * @param ids 需要删除的备份记录表ID
     * @return 结果
     */
    @Override
    public int deleteSqBackupBookmarkLogByIds(Long[] ids)
    {
        return sqBackupBookmarkLogMapper.deleteSqBackupBookmarkLogByIds(ids);
    }

    /**
     * 删除备份记录表信息
     * 
     * @param id 备份记录表ID
     * @return 结果
     */
    @Override
    public int deleteSqBackupBookmarkLogById(Long id)
    {
        return sqBackupBookmarkLogMapper.deleteSqBackupBookmarkLogById(id);
    }
}
