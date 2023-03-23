package com.ruoyi.course.service.impl;

import java.util.List;

import com.ruoyi.course.domain.CourCourse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.course.mapper.CourUserCourseSectionMapper;
import com.ruoyi.course.domain.CourUserCourseSection;
import com.ruoyi.course.service.ICourUserCourseSectionService;

/**
 * 用户-课程-章节关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
@Service
public class CourUserCourseSectionServiceImpl implements ICourUserCourseSectionService 
{
    @Autowired
    private CourUserCourseSectionMapper courUserCourseSectionMapper;

    /**
     * 查询用户-课程-章节关系
     * 
     * @param id 用户-课程-章节关系主键
     * @return 用户-课程-章节关系
     */
    @Override
    public CourUserCourseSection selectCourUserCourseSectionById(Integer id)
    {
        return courUserCourseSectionMapper.selectCourUserCourseSectionById(id);
    }

    /**
     * 查询用户-课程-章节关系列表
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 用户-课程-章节关系
     */
    @Override
    public List<CourUserCourseSection> selectCourUserCourseSectionList(CourUserCourseSection courUserCourseSection)
    {
        return courUserCourseSectionMapper.selectCourUserCourseSectionList(courUserCourseSection);
    }

    /**
     * 根据用户ID查询课程列表
     *
     * @param userId 用户ID
     * @return 课程集合
     */
    public List<CourCourse> getCourseListByUserId(Integer userId)
    {
        return courUserCourseSectionMapper.getCourseListByUserId(userId);
    }

    /**
     * 新增用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    @Override
    public int insertCourUserCourseSection(CourUserCourseSection courUserCourseSection)
    {
        return courUserCourseSectionMapper.insertCourUserCourseSection(courUserCourseSection);
    }

    /**
     * 修改用户-课程-章节关系
     * 
     * @param courUserCourseSection 用户-课程-章节关系
     * @return 结果
     */
    @Override
    public int updateCourUserCourseSection(CourUserCourseSection courUserCourseSection)
    {
        return courUserCourseSectionMapper.updateCourUserCourseSection(courUserCourseSection);
    }

    /**
     * 批量删除用户-课程-章节关系
     * 
     * @param ids 需要删除的用户-课程-章节关系主键
     * @return 结果
     */
    @Override
    public int deleteCourUserCourseSectionByIds(Integer[] ids)
    {
        return courUserCourseSectionMapper.deleteCourUserCourseSectionByIds(ids);
    }

    /**
     * 删除用户-课程-章节关系信息
     * 
     * @param id 用户-课程-章节关系主键
     * @return 结果
     */
    @Override
    public int deleteCourUserCourseSectionById(Integer id)
    {
        return courUserCourseSectionMapper.deleteCourUserCourseSectionById(id);
    }
}
