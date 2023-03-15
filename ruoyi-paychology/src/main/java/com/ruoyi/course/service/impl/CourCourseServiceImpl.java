package com.ruoyi.course.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.course.domain.CourCourse;
import com.ruoyi.course.mapper.CourCourseMapper;
import com.ruoyi.course.service.ICourCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 课程Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-03-14
 */
@Service
public class CourCourseServiceImpl implements ICourCourseService
{
    @Autowired
    private CourCourseMapper courCourseMapper;

    /**
     * 查询课程
     * 
     * @param id 课程主键
     * @return 课程
     */
    @Override
    public CourCourse selectCourCourseById(Long id)
    {
        return courCourseMapper.selectCourCourseById(id);
    }

    /**
     * 查询课程列表
     * 
     * @param courCourse 课程
     * @return 课程
     */
    @Override
    public List<CourCourse> selectCourCourseList(CourCourse courCourse)
    {
        return courCourseMapper.selectCourCourseList(courCourse);
    }

    /**
     * 新增课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    @Override
    public int insertCourCourse(CourCourse courCourse)
    {
        courCourse.setCreateTime(DateUtils.getNowDate());
        return courCourseMapper.insertCourCourse(courCourse);
    }

    /**
     * 修改课程
     * 
     * @param courCourse 课程
     * @return 结果
     */
    @Override
    public int updateCourCourse(CourCourse courCourse)
    {
        courCourse.setUpdateTime(DateUtils.getNowDate());
        return courCourseMapper.updateCourCourse(courCourse);
    }

    /**
     * 批量删除课程
     * 
     * @param ids 需要删除的课程主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseByIds(Long[] ids)
    {
        return courCourseMapper.deleteCourCourseByIds(ids);
    }

    /**
     * 删除课程信息
     * 
     * @param id 课程主键
     * @return 结果
     */
    @Override
    public int deleteCourCourseById(Long id)
    {
        return courCourseMapper.deleteCourCourseById(id);
    }
}
