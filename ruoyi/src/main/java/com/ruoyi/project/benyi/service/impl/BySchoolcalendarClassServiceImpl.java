package com.ruoyi.project.benyi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.BySchoolcalendarClassMapper;
import com.ruoyi.project.benyi.domain.BySchoolcalendarClass;
import com.ruoyi.project.benyi.service.IBySchoolcalendarClassService;

/**
 * 园历管理(班级)Service业务层处理
 * 
 * @author tsbz
 * @date 2020-05-06
 */
@Service
public class BySchoolcalendarClassServiceImpl implements IBySchoolcalendarClassService 
{
    @Autowired
    private BySchoolcalendarClassMapper bySchoolcalendarClassMapper;

    /**
     * 查询园历管理(班级)
     * 
     * @param id 园历管理(班级)ID
     * @return 园历管理(班级)
     */
    @Override
    public BySchoolcalendarClass selectBySchoolcalendarClassById(Long id)
    {
        return bySchoolcalendarClassMapper.selectBySchoolcalendarClassById(id);
    }

    /**
     * 查询园历管理(班级)列表
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 园历管理(班级)
     */
    @Override
    public List<BySchoolcalendarClass> selectBySchoolcalendarClassList(BySchoolcalendarClass bySchoolcalendarClass)
    {
        return bySchoolcalendarClassMapper.selectBySchoolcalendarClassList(bySchoolcalendarClass);
    }

    /**
     * 新增园历管理(班级)
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 结果
     */
    @Override
    public int insertBySchoolcalendarClass(BySchoolcalendarClass bySchoolcalendarClass)
    {
        return bySchoolcalendarClassMapper.insertBySchoolcalendarClass(bySchoolcalendarClass);
    }

    /**
     * 修改园历管理(班级)
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 结果
     */
    @Override
    public int updateBySchoolcalendarClass(BySchoolcalendarClass bySchoolcalendarClass)
    {
        return bySchoolcalendarClassMapper.updateBySchoolcalendarClass(bySchoolcalendarClass);
    }

    /**
     * 批量删除园历管理(班级)
     * 
     * @param ids 需要删除的园历管理(班级)ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolcalendarClassByIds(Long[] ids)
    {
        return bySchoolcalendarClassMapper.deleteBySchoolcalendarClassByIds(ids);
    }

    /**
     * 删除园历管理(班级)信息
     * 
     * @param id 园历管理(班级)ID
     * @return 结果
     */
    @Override
    public int deleteBySchoolcalendarClassById(Long id)
    {
        return bySchoolcalendarClassMapper.deleteBySchoolcalendarClassById(id);
    }
}
