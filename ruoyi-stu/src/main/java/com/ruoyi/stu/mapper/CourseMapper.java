package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.Course;

import java.util.List;

public interface CourseMapper {
    List<Course> selectAllCourses(Course course);
    Integer updateCourse(Course course);
    Integer addCourse(Course course);
    Integer delCourse(int[] ids);
}
