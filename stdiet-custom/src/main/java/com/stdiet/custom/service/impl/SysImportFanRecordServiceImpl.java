package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysImportFanRecordMapper;
import com.stdiet.custom.domain.SysImportFanRecord;
import com.stdiet.custom.service.ISysImportFanRecordService;

/**
 * 导粉管理Service业务层处理
 *
 * @author xzj
 * @date 2021-05-17
 */
@Service
public class SysImportFanRecordServiceImpl implements ISysImportFanRecordService
{
    @Autowired
    private SysImportFanRecordMapper sysImportFanRecordMapper;

    /**
     * 查询导粉管理
     *
     * @param id 导粉管理ID
     * @return 导粉管理
     */
    @Override
    public SysImportFanRecord selectSysImportFanRecordById(Long id)
    {
        return sysImportFanRecordMapper.selectSysImportFanRecordById(id);
    }

    /**
     * 查询导粉管理列表
     *
     * @param sysImportFanRecord 导粉管理
     * @return 导粉管理
     */
    @Override
    public List<SysImportFanRecord> selectSysImportFanRecordList(SysImportFanRecord sysImportFanRecord)
    {
        return sysImportFanRecordMapper.selectSysImportFanRecordList(sysImportFanRecord);
    }

    /**
     * 新增导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    @Override
    public int insertSysImportFanRecord(SysImportFanRecord sysImportFanRecord)
    {
        sysImportFanRecord.setCreateTime(DateUtils.getNowDate());
        return sysImportFanRecordMapper.insertSysImportFanRecord(sysImportFanRecord);
    }

    /**
     * 修改导粉管理
     *
     * @param sysImportFanRecord 导粉管理
     * @return 结果
     */
    @Override
    public int updateSysImportFanRecord(SysImportFanRecord sysImportFanRecord)
    {
        sysImportFanRecord.setUpdateTime(DateUtils.getNowDate());
        return sysImportFanRecordMapper.updateSysImportFanRecord(sysImportFanRecord);
    }

    /**
     * 批量删除导粉管理
     *
     * @param ids 需要删除的导粉管理ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanRecordByIds(Long[] ids)
    {
        return sysImportFanRecordMapper.deleteSysImportFanRecordByIds(ids);
    }

    /**
     * 删除导粉管理信息
     *
     * @param id 导粉管理ID
     * @return 结果
     */
    @Override
    public int deleteSysImportFanRecordById(Long id)
    {
        return sysImportFanRecordMapper.deleteSysImportFanRecordById(id);
    }
}