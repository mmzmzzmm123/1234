package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.BySchoolcalendar;

/**
 * 园历管理Service接口
 * 
 * @author tsbz
 * @date 2020-04-20
 */
public interface IBySchoolcalendarService 
{
    /**
     * 查询园历管理
     * 
     * @param id 园历管理ID
     * @return 园历管理
     */
    public BySchoolcalendar selectBySchoolcalendarById(Long id);

    /**
     * 查询园历管理列表
     * 
     * @param bySchoolcalendar 园历管理
     * @return 园历管理集合
     */
    public List<BySchoolcalendar> selectBySchoolcalendarList(BySchoolcalendar bySchoolcalendar);

    /**
     * 新增园历管理
     * 
     * @param bySchoolcalendar 园历管理
     * @return 结果
     */
    public int insertBySchoolcalendar(BySchoolcalendar bySchoolcalendar);

    /**
     * 修改园历管理
     * 
     * @param bySchoolcalendar 园历管理
     * @return 结果
     */
    public int updateBySchoolcalendar(BySchoolcalendar bySchoolcalendar);

    /**
     * 批量删除园历管理
     * 
     * @param ids 需要删除的园历管理ID
     * @return 结果
     */
    public int deleteBySchoolcalendarByIds(Long[] ids);

    /**
     * 删除园历管理信息
     * 
     * @param id 园历管理ID
     * @return 结果
     */
    public int deleteBySchoolcalendarById(Long id);
}
