package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByCalendarMapper;
import com.ruoyi.project.benyi.domain.ByCalendar;
import com.ruoyi.project.benyi.service.IByCalendarService;

/**
 * 园历管理(本一)Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-10
 */
@Service
public class ByCalendarServiceImpl implements IByCalendarService 
{
    @Autowired
    private ByCalendarMapper byCalendarMapper;

    /**
     * 查询园历管理(本一)
     * 
     * @param id 园历管理(本一)ID
     * @return 园历管理(本一)
     */
    @Override
    public ByCalendar selectByCalendarById(Long id)
    {
        return byCalendarMapper.selectByCalendarById(id);
    }

    /**
     * 查询园历管理(本一)列表
     * 
     * @param byCalendar 园历管理(本一)
     * @return 园历管理(本一)
     */
    @Override
    public List<ByCalendar> selectByCalendarList(ByCalendar byCalendar)
    {
        return byCalendarMapper.selectByCalendarList(byCalendar);
    }

    /**
     * 新增园历管理(本一)
     * 
     * @param byCalendar 园历管理(本一)
     * @return 结果
     */
    @Override
    public int insertByCalendar(ByCalendar byCalendar)
    {
        return byCalendarMapper.insertByCalendar(byCalendar);
    }

    /**
     * 修改园历管理(本一)
     * 
     * @param byCalendar 园历管理(本一)
     * @return 结果
     */
    @Override
    public int updateByCalendar(ByCalendar byCalendar)
    {
        return byCalendarMapper.updateByCalendar(byCalendar);
    }

    /**
     * 批量删除园历管理(本一)
     * 
     * @param ids 需要删除的园历管理(本一)ID
     * @return 结果
     */
    @Override
    public int deleteByCalendarByIds(Long[] ids)
    {
        return byCalendarMapper.deleteByCalendarByIds(ids);
    }

    /**
     * 删除园历管理(本一)信息
     * 
     * @param id 园历管理(本一)ID
     * @return 结果
     */
    @Override
    public int deleteByCalendarById(Long id)
    {
        return byCalendarMapper.deleteByCalendarById(id);
    }
}
