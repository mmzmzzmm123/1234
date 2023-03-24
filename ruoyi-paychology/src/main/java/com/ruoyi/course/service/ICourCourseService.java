package com.ruoyi.course.service;

import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.vo.CourseVO;

import java.util.List;

/**
 * 课程Service接口
 *
 * @author ruoyi
 * @date 2023-03-14
 */
public interface ICourCourseService
{
    /**
     * 查询课程
     *
     * @param id 课程主键
     * @return 课程
     */
    public CourCourse selectCourCourseById(Integer id);

    /**
     * 课程分类查询
     *
     * @param typeId 课程主键
     * @return 课程
     */
    public CourCourse selectCourCourseByType(Integer typeId);

    /**
     * 查询课程列表
     *
     * @param courCourse 课程
     * @return 课程集合
     */
    public List<CourCourse> selectCourCourseList(CourCourse courCourse);

    /**
     * 查询课程
     *
     * @param courCourse 课程
     * @return 课程集合
     */
    public CourCourse  selectCourCourseByCourseId(String courseId);

    /**
     * 根据课程的章节计算课程信息
     *
     * @param courseId 课程ID
     * @return 课程信息
     */
    public boolean calCourCourseList(Integer courseId);

    /**
     * 根据课程的章节计算课程学习时长
     *
     * @param courseId 课程ID
     * @return 课程信息
     */
    public Integer calCourCourseStudyDuration(Integer courseId);
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
     * 批量删除课程
     * 
     * @param ids 需要删除的课程主键集合
     * @return 结果
     */
    public int deleteCourCourseByIds(Integer[] ids);

    /**
     * 删除课程信息
     * 
     * @param id 课程主键
     * @return 结果
     */
    public int deleteCourCourseById(Integer id);

    /**
     * 查询课程是否支付
     *
     * @param courseId 课程ID
     * @return 课程支付数量
     */
    public int getPaidCourseCount(Integer courseId);
}
