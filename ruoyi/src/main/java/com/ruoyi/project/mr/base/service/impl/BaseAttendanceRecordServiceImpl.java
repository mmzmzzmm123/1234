package com.ruoyi.project.mr.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mr.base.mapper.BaseAttendanceRecordMapper;
import com.ruoyi.project.mr.base.domain.BaseAttendanceRecord;
import com.ruoyi.project.mr.base.service.IBaseAttendanceRecordService;

/**
 * 考勤记录Service业务层处理
 * 
 * @author mr
 * @date 2020-02-07
 */
@Service
public class BaseAttendanceRecordServiceImpl implements IBaseAttendanceRecordService 
{
    @Autowired
    private BaseAttendanceRecordMapper baseAttendanceRecordMapper;

    /**
     * 查询考勤记录
     * 
     * @param id 考勤记录ID
     * @return 考勤记录
     */
    @Override
    public BaseAttendanceRecord selectBaseAttendanceRecordById(String id)
    {
        return baseAttendanceRecordMapper.selectBaseAttendanceRecordById(id);
    }

    /**
     * 查询考勤记录列表
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 考勤记录
     */
    @Override
    public List<BaseAttendanceRecord> selectBaseAttendanceRecordList(BaseAttendanceRecord baseAttendanceRecord)
    {
        return baseAttendanceRecordMapper.selectBaseAttendanceRecordList(baseAttendanceRecord);
    }

    /**
     * 新增考勤记录
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int insertBaseAttendanceRecord(BaseAttendanceRecord baseAttendanceRecord)
    {
        baseAttendanceRecord.setCreateTime(DateUtils.getNowDate());
        return baseAttendanceRecordMapper.insertBaseAttendanceRecord(baseAttendanceRecord);
    }

    /**
     * 修改考勤记录
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int updateBaseAttendanceRecord(BaseAttendanceRecord baseAttendanceRecord)
    {
        baseAttendanceRecord.setUpdateTime(DateUtils.getNowDate());
        return baseAttendanceRecordMapper.updateBaseAttendanceRecord(baseAttendanceRecord);
    }

    /**
     * 批量删除考勤记录
     * 
     * @param ids 需要删除的考勤记录ID
     * @return 结果
     */
    @Override
    public int deleteBaseAttendanceRecordByIds(String[] ids)
    {
        return baseAttendanceRecordMapper.deleteBaseAttendanceRecordByIds(ids);
    }

    /**
     * 删除考勤记录信息
     * 
     * @param id 考勤记录ID
     * @return 结果
     */
    @Override
    public int deleteBaseAttendanceRecordById(String id)
    {
        return baseAttendanceRecordMapper.deleteBaseAttendanceRecordById(id);
    }
}
