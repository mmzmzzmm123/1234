package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.StuCoursePlan;
import com.ruoyi.stu.domain.StuSemester;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStuCoursePlanService {
    List<StuCoursePlan> findAll(String stuCls,String teaName, String courseName);
    /** 查询各个年级的课程 **/
    List<StuCoursePlan> findAllLevelCourse( String clsYear, String courseName,String semesterName);
    /**  批量添加年级课程   **/
    Integer addCoursePlans(Integer semesterId,String clsYear,Integer courseId);
    /**  查询所有学期数量  **/
    List<StuSemester> findAllSemester();
    /** 查询所有课程 **/
    List<Course> findAllCourses();
}
