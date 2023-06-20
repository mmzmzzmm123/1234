package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.StuCoursePlan;
import com.ruoyi.stu.domain.StuSemester;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuCoursePlanMapper {
    /** 查询各个年级各个班级的课程 **/
    List<StuCoursePlan> findAll(@Param("stuCls") String stuCls,@Param("teaName") String teaName,@Param("courseName") String courseName);
    /** 查询各个年级的课程 **/
    List<StuCoursePlan> findAllLevelCourse(@Param("clsYear") String clsYear,@Param("courseName") String courseName,@Param("semesterName")String semesterName);
    /** 添加年级学期授课课程 **/
    Integer addCoursePlans(@Param("coursePlans")List<StuCoursePlan> coursePlans);
    /** 查询所有学期  **/
    List<StuSemester> findAllSemester();
    /** 查询所有课程 **/
    List<Course> findAllCourses();

}
