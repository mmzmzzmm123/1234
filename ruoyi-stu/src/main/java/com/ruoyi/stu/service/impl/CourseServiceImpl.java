package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.mapper.CourseMapper;
import com.ruoyi.stu.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> selectAllCourses(Course course) {
        return courseMapper.selectAllCourses(course);
    }

    @Override
    public Integer updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public Integer addCourse(Course course) {
        return courseMapper.addCourse(course);
    }

    @Override
    public Integer delCourse(int[] ids) {
        return courseMapper.delCourse(ids);
    }
}
