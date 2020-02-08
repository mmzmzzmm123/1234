package com.ruoyi.project.mr.base.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.mr.base.domain.BaseCalendar;
import com.ruoyi.project.mr.base.mapper.BaseCalendarMapper;
import com.ruoyi.project.mr.base.service.IBaseCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考勤日历Service业务层处理
 *
 * @author mr
 * @date 2020-01-07
 */
@Service
public class BaseCalendarServiceImpl implements IBaseCalendarService
{
    @Autowired
    private BaseCalendarMapper baseCalendarMapper;

    /**
     * 查询考勤日历
     *
     * @param id 考勤日历ID
     * @return 考勤日历
     */
    @Override
    public BaseCalendar selectBaseCalendarById(String id)
    {
        return baseCalendarMapper.selectBaseCalendarById(id);
    }

    /**
     * 查询考勤日历列表
     *
     * @param baseCalendar 考勤日历
     * @return 考勤日历
     */
    @Override
    public List<BaseCalendar> selectBaseCalendarList(BaseCalendar baseCalendar)
    {
        return baseCalendarMapper.selectBaseCalendarList(baseCalendar);
    }

    /**
     * 新增考勤日历
     *
     * @param baseCalendar 考勤日历
     * @return 结果
     */
    @Override
    public int insertBaseCalendar(BaseCalendar baseCalendar)
    {
        baseCalendar.setCreateTime(DateUtils.getNowDate());
        return baseCalendarMapper.insertBaseCalendar(baseCalendar);
    }

    /**
     * 修改考勤日历
     *
     * @param baseCalendar 考勤日历
     * @return 结果
     */
    @Override
    public int updateBaseCalendar(BaseCalendar baseCalendar)
    {
        baseCalendar.setUpdateTime(DateUtils.getNowDate());
        return baseCalendarMapper.updateBaseCalendar(baseCalendar);
    }

    /**
     * 批量删除考勤日历
     *
     * @param ids 需要删除的考勤日历ID
     * @return 结果
     */
    @Override
    public int deleteBaseCalendarByIds(String[] ids)
    {
        return baseCalendarMapper.deleteBaseCalendarByIds(ids);
    }

    /**
     * 删除考勤日历信息
     *
     * @param id 考勤日历ID
     * @return 结果
     */
    @Override
    public int deleteBaseCalendarById(String id)
    {
        return baseCalendarMapper.deleteBaseCalendarById(id);
    }
}
