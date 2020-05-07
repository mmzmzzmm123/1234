package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.BySchoolcalendarClass;

/**
 * 园历管理(班级)Mapper接口
 * 
 * @author tsbz
 * @date 2020-05-06
 */
public interface BySchoolcalendarClassMapper 
{
    /**
     * 查询园历管理(班级)
     * 
     * @param id 园历管理(班级)ID
     * @return 园历管理(班级)
     */
    public BySchoolcalendarClass selectBySchoolcalendarClassById(Long id);

    /**
     * 查询园历管理(班级)列表
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 园历管理(班级)集合
     */
    public List<BySchoolcalendarClass> selectBySchoolcalendarClassList(BySchoolcalendarClass bySchoolcalendarClass);

    /**
     * 新增园历管理(班级)
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 结果
     */
    public int insertBySchoolcalendarClass(BySchoolcalendarClass bySchoolcalendarClass);

    /**
     * 修改园历管理(班级)
     * 
     * @param bySchoolcalendarClass 园历管理(班级)
     * @return 结果
     */
    public int updateBySchoolcalendarClass(BySchoolcalendarClass bySchoolcalendarClass);

    /**
     * 删除园历管理(班级)
     * 
     * @param id 园历管理(班级)ID
     * @return 结果
     */
    public int deleteBySchoolcalendarClassById(Long id);

    /**
     * 批量删除园历管理(班级)
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBySchoolcalendarClassByIds(Long[] ids);
}
