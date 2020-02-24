package com.ruoyi.project.mr.base.mapper;

import com.ruoyi.project.mr.base.domain.BaseAttendanceRecord;
import java.util.List;

/**
 * 考勤记录Mapper接口
 * 
 * @author mr
 * @date 2020-02-07
 */
public interface BaseAttendanceRecordMapper 
{
    /**
     * 查询考勤记录
     * 
     * @param id 考勤记录ID
     * @return 考勤记录
     */
    public BaseAttendanceRecord selectBaseAttendanceRecordById(String id);

    /**
     * 查询考勤记录列表
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 考勤记录集合
     */
    public List<BaseAttendanceRecord> selectBaseAttendanceRecordList(BaseAttendanceRecord baseAttendanceRecord);

    /**
     * 新增考勤记录
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 结果
     */
    public int insertBaseAttendanceRecord(BaseAttendanceRecord baseAttendanceRecord);

    /**
     * 修改考勤记录
     * 
     * @param baseAttendanceRecord 考勤记录
     * @return 结果
     */
    public int updateBaseAttendanceRecord(BaseAttendanceRecord baseAttendanceRecord);

    /**
     * 删除考勤记录
     * 
     * @param id 考勤记录ID
     * @return 结果
     */
    public int deleteBaseAttendanceRecordById(String id);

    /**
     * 批量删除考勤记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBaseAttendanceRecordByIds(String[] ids);
}
