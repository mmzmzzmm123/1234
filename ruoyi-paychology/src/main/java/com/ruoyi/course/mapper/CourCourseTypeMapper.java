package com.ruoyi.course.mapper;

import com.ruoyi.course.domain.CourCourseType;

import java.util.List;

/**
 * 课程类型Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public interface CourCourseTypeMapper 
{
    /**
     * 查询课程类型
     * 
     * @param typeId 课程类型主键
     * @return 课程类型
     */
    public CourCourseType selectCourCourseTypeByTypeId(Integer typeId);

    /**
     * 查询课程类型列表
     * 
     * @param courCourseType 课程类型
     * @return 课程类型集合
     */
    public List<CourCourseType> selectCourCourseTypeList(CourCourseType courCourseType);

    /**
     * 新增课程类型
     * 
     * @param courCourseType 课程类型
     * @return 结果
     */
    public int insertCourCourseType(CourCourseType courCourseType);

    /**
     * 修改课程类型
     * 
     * @param courCourseType 课程类型
     * @return 结果
     */
    public int updateCourCourseType(CourCourseType courCourseType);

    /**
     * 删除课程类型
     * 
     * @param typeId 课程类型主键
     * @return 结果
     */
    public int deleteCourCourseTypeByTypeId(Integer typeId);

    /**
     * 批量删除课程类型
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourCourseTypeByTypeIds(Integer[] typeIds);
}
