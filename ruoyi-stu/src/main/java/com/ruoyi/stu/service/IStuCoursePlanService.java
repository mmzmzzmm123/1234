package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.StuCoursePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStuCoursePlanService {
    List<StuCoursePlan> findAll(String stuCls,String teaName, String courseName);
    /** 查询各个年级的课程 **/
    List<StuCoursePlan> findAllLevelCourse(@Param("clsYear") String clsYear, @Param("courseName") String courseName);
}
