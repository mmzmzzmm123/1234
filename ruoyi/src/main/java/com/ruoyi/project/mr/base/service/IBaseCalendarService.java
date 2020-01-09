package com.ruoyi.project.mr.base.service;

import com.ruoyi.project.mr.base.domain.BaseCalendar;
import java.util.List;

/**
 * 考勤日历Service接口
 *
 * @author mr
 * @date 2020-01-07
 */
public interface IBaseCalendarService
{
    /**
     * 查询考勤日历
     *
     * @param id 考勤日历ID
     * @return 考勤日历
     */
    public BaseCalendar selectBaseCalendarById(String id);

    /**
     * 查询考勤日历列表
     *
     * @param baseCalendar 考勤日历
     * @return 考勤日历集合
     */
    public List<BaseCalendar> selectBaseCalendarList(BaseCalendar baseCalendar);

    /**
     * 新增考勤日历
     *
     * @param baseCalendar 考勤日历
     * @return 结果
     */
    public int insertBaseCalendar(BaseCalendar baseCalendar);

    /**
     * 修改考勤日历
     *
     * @param baseCalendar 考勤日历
     * @return 结果
     */
    public int updateBaseCalendar(BaseCalendar baseCalendar);

    /**
     * 批量删除考勤日历
     *
     * @param ids 需要删除的考勤日历ID
     * @return 结果
     */
    public int deleteBaseCalendarByIds(String[] ids);

    /**
     * 删除考勤日历信息
     *
     * @param id 考勤日历ID
     * @return 结果
     */
    public int deleteBaseCalendarById(String id);

}
