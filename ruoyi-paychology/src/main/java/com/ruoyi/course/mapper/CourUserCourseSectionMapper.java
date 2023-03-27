package com.ruoyi.course.mapper;

import java.util.List;

import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.domain.CourUserCourseSection;

/**
 * 用户-课程-章节关系Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface CourUserCourseSectionMapper 
{
    /**
     * 查询用户-课程-章节关系
     * 
     * @param id 用户-课程-章节关系主键
     * @return 用户-课程-章节关系
     */
    public CourUserCourseSection selectCourUserCourseSectionById(Integer id);

    /**
     * 查询用户-课程-章节关系列表
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 用户-课程-章节关系集合
     */
    public List<CourUserCourseSection> selectCourUserCourseSectionList(CourUserCourseSection courUserCourseSection);


    /**
     * 新增用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    public int insertCourUserCourseSection(CourUserCourseSection courUserCourseSection);

    /**
     * 修改用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    public int updateCourUserCourseSection(CourUserCourseSection courUserCourseSection);

    /**
     * 删除用户-课程-章节关系
     * 
     * @param id 用户-课程-章节关系主键
     * @return 结果
     */
    public int deleteCourUserCourseSectionById(Integer id);

    /**
     * 批量删除用户-课程-章节关系
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourUserCourseSectionByIds(Integer[] ids);

    /**
     * 记录用户学习课程章节的结束时间点
     *
     * @param userCourseSection 用户-课程-章节关系
     * @return 结果
     */
    int recordEndTime(CourUserCourseSection userCourseSection);
}
