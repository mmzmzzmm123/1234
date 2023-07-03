package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Course;

import java.util.List;

public interface ICourseService {
    List<Course> selectAllCourses(Course course);
    Integer updateCourse(Course course);
    Integer addCourse(Course course);
    Integer delCourse(int[] ids);
}
