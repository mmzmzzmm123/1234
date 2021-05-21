package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysImportFanRecord;

/**
 * 导粉管理Service接口
 *
 * @author xzj
 * @date 2021-05-17
 */
public interface ISysImportFanRecordService
{
    /**
     * 查询导粉管理
     *
     * @param id 导粉管理ID
     * @return 导粉管理
     */
    public SysImportFanRecord selectSysImportFanRecordById(Long id);

    /**
     * 查询导粉管理列表
     *
     * @param sysImportFanRecord 导粉管理
     * @return 导粉管理集合
     */
    public List<SysImportFanRecord> selectSysImportFanRecordList(SysImportFanRecord sysImportFanRecord);

    /**
     * 新增导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    public int insertSysImportFanRecord(SysImportFanRecord sysImportFanRecord);

    /**
     * 修改导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    public int updateSysImportFanRecord(SysImportFanRecord sysImportFanRecord);

    /**
     * 批量删除导粉管理
     *
     * @param ids 需要删除的导粉管理ID
     * @return 结果
     */
    public int deleteSysImportFanRecordByIds(Long[] ids);

    /**
     * 删除导粉管理信息
     *
     * @param id 导粉管理ID
     * @return 结果
     */
    public int deleteSysImportFanRecordById(Long id);

    //查询总导粉数量
    int selectTotalSysImportFanNum(SysImportFanRecord sysImportFanRecord);
}