package com.ruoyi.course.mapper;

import com.ruoyi.course.domain.CourCourse;

import java.util.List;

/**
 * 课程Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
public interface CourCourseMapper 
{
    /**
     * 查询课程
     * 
     * @param id 课程主键
     * @return 课程
     */
    public CourCourse selectCourCourseById(Long id);

    /**
     * 课程分类查询
     *
     * @param typeId 课程主键
     * @return 课程
     */
    public CourCourse selectCourCourseByType(Long typeId);

    /**
     * 查询课程列表
     * 
     * @param courCourse 课程
     * @return 课程集合
     */
    public List<CourCourse> selectCourCourseList(CourCourse courCourse);

    /**
     * 新增课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    public int insertCourCourse(CourCourse courCourse);

    /**
     * 修改课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    public int updateCourCourse(CourCourse courCourse);

    /**
     * 删除课程
     * 
     * @param id 课程主键
     * @return 结果
     */
    public int deleteCourCourseById(Long id);

    /**
     * 批量删除课程
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourCourseByIds(Long[] ids);
}
