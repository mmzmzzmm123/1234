package com.ruoyi.project.mr.base.service;

import com.ruoyi.project.mr.base.domain.BaseCalendarItem;

import java.util.List;

/**
 * 考勤日历明细Service接口
 *
 * @author mr
 * @date 2020-01-07
 */
public interface IBaseCalendarItemService
{
    /**
     * 查询考勤日历明细
     *
     * @param id 考勤日历明细ID
     * @return 考勤日历明细
     */
    public BaseCalendarItem selectBaseCalendarItemById(String id);

    /**
     * 查询考勤日历明细列表
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 考勤日历明细集合
     */
    public List<BaseCalendarItem> selectBaseCalendarItemList(BaseCalendarItem baseCalendarItem);

    /**
     * 新增考勤日历明细
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 结果
     */
    public int insertBaseCalendarItem(BaseCalendarItem baseCalendarItem);

    /**
     * 修改考勤日历明细
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 结果
     */
    public int updateBaseCalendarItem(BaseCalendarItem baseCalendarItem);

    /**
     * 批量删除考勤日历明细
     *
     * @param ids 需要删除的考勤日历明细ID
     * @return 结果
     */
    public int deleteBaseCalendarItemByIds(String[] ids);

    /**
     * 删除考勤日历明细信息
     *
     * @param id 考勤日历明细ID
     * @return 结果
     */
    public int deleteBaseCalendarItemById(String id);
}
