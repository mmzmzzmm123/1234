package com.ruoyi.bookmark.service;

import java.util.List;
import com.ruoyi.bookmark.domain.SqBackupBookmarkLog;

/**
 * 备份记录表Service接口
 * 
 * @author wanghao
 * @date 2021-11-22
 */
public interface ISqBackupBookmarkLogService 
{
    /**
     * 查询备份记录表
     * 
     * @param id 备份记录表ID
     * @return 备份记录表
     */
    public SqBackupBookmarkLog selectSqBackupBookmarkLogById(Long id);

    /**
     * 查询备份记录表列表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 备份记录表集合
     */
    public List<SqBackupBookmarkLog> selectSqBackupBookmarkLogList(SqBackupBookmarkLog sqBackupBookmarkLog);

    /**
     * 新增备份记录表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 结果
     */
    public int insertSqBackupBookmarkLog(SqBackupBookmarkLog sqBackupBookmarkLog);

    /**
     * 修改备份记录表
     * 
     * @param sqBackupBookmarkLog 备份记录表
     * @return 结果
     */
    public int updateSqBackupBookmarkLog(SqBackupBookmarkLog sqBackupBookmarkLog);

    /**
     * 批量删除备份记录表
     * 
     * @param ids 需要删除的备份记录表ID
     * @return 结果
     */
    public int deleteSqBackupBookmarkLogByIds(Long[] ids);

    /**
     * 删除备份记录表信息
     * 
     * @param id 备份记录表ID
     * @return 结果
     */
    public int deleteSqBackupBookmarkLogById(Long id);
}
