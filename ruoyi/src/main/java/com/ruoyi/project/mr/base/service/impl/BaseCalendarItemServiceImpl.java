package com.ruoyi.project.mr.base.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.mr.base.domain.BaseCalendarItem;
import com.ruoyi.project.mr.base.mapper.BaseCalendarItemMapper;
import com.ruoyi.project.mr.base.service.IBaseCalendarItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考勤日历明细Service业务层处理
 *
 * @author mr
 * @date 2020-01-07
 */
@Service
public class BaseCalendarItemServiceImpl implements IBaseCalendarItemService
{
    @Autowired
    private BaseCalendarItemMapper baseCalendarItemMapper;

    /**
     * 查询考勤日历明细
     *
     * @param id 考勤日历明细ID
     * @return 考勤日历明细
     */
    @Override
    public BaseCalendarItem selectBaseCalendarItemById(String id)
    {
        return baseCalendarItemMapper.selectBaseCalendarItemById(id);
    }

    /**
     * 查询考勤日历明细列表
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 考勤日历明细
     */
    @Override
    public List<BaseCalendarItem> selectBaseCalendarItemList(BaseCalendarItem baseCalendarItem)
    {
        return baseCalendarItemMapper.selectBaseCalendarItemList(baseCalendarItem);
    }

    /**
     * 新增考勤日历明细
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 结果
     */
    @Override
    public int insertBaseCalendarItem(BaseCalendarItem baseCalendarItem)
    {
        baseCalendarItem.setCreateTime(DateUtils.getNowDate());
        return baseCalendarItemMapper.insertBaseCalendarItem(baseCalendarItem);
    }

    /**
     * 修改考勤日历明细
     *
     * @param baseCalendarItem 考勤日历明细
     * @return 结果
     */
    @Override
    public int updateBaseCalendarItem(BaseCalendarItem baseCalendarItem)
    {
        baseCalendarItem.setUpdateTime(DateUtils.getNowDate());
        return baseCalendarItemMapper.updateBaseCalendarItem(baseCalendarItem);
    }

    /**
     * 批量删除考勤日历明细
     *
     * @param ids 需要删除的考勤日历明细ID
     * @return 结果
     */
    @Override
    public int deleteBaseCalendarItemByIds(String[] ids)
    {
        return baseCalendarItemMapper.deleteBaseCalendarItemByIds(ids);
    }

    /**
     * 删除考勤日历明细信息
     *
     * @param id 考勤日历明细ID
     * @return 结果
     */
    @Override
    public int deleteBaseCalendarItemById(String id)
    {
        return baseCalendarItemMapper.deleteBaseCalendarItemById(id);
    }
}
