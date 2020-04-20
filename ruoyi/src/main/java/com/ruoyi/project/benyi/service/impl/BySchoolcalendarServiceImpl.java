package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.BySchoolcalendarMapper;
import com.ruoyi.project.benyi.domain.BySchoolcalendar;
import com.ruoyi.project.benyi.service.IBySchoolcalendarService;

/**
 * 园历管理Service业务层处理
 * 
 * @author tsbz
 * @date 2020-04-20
 */
@Service
public class BySchoolcalendarServiceImpl implements IBySchoolcalendarService 
{
    @Autowired
    private BySchoolcalendarMapper bySchoolcalendarMapper;

    /**
     * 查询园历管理
     * 
     * @param id 园历管理ID
     * @return 园历管理
     */
    @Override
    public BySchoolcalendar selectBySchoolcalendarById(Long id)
    {
        return bySchoolcalendarMapper.selectBySchoolcalendarById(id);
    }

    /**
     * 查询园历管理列表
     * 
     * @param bySchoolcalendar 园历管理
     * @return 园历管理
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<BySchoolcalendar> selectBySchoolcalendarList(BySchoolcalendar bySchoolcalendar)
    {
        return bySchoolcalendarMapper.selectBySchoolcalendarList(bySchoolcalendar);
    }

    /**
     * 新增园历管理
     * 
     * @param bySchoolcalendar 园历管理
     * @return 结果
     */
    @Override
    public int insertBySchoolcalendar(BySchoolcalendar bySchoolcalendar)
    {
        return bySchoolcalendarMapper.insertBySchoolcalendar(bySchoolcalendar);
    }

    /**
     * 修改园历管理
     * 
     * @param bySchoolcalendar 园历管理
     * @return 结果
     */
    @Override
    public int updateBySchoolcalendar(BySchoolcalendar bySchoolcalendar)
    {
        return bySchoolcalendarMapper.updateBySchoolcalendar(bySchoolcalendar);
    }

    /**
     * 批量删除园历管理
     * 
     * @param ids 需要删除的园历管理ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolcalendarByIds(Long[] ids)
    {
        return bySchoolcalendarMapper.deleteBySchoolcalendarByIds(ids);
    }

    /**
     * 删除园历管理信息
     * 
     * @param id 园历管理ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolcalendarById(Long id)
    {
        return bySchoolcalendarMapper.deleteBySchoolcalendarById(id);
    }
}
