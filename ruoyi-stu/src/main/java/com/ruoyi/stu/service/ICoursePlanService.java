package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.CoursePlan;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.vo.CoursePlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICoursePlanService {
    List<CoursePlan> selectCoursePlan(CoursePlan coursePlan);
    List<CoursePlanVO> selectCoursePlanVO(CoursePlan coursePlan);
    //批量添加
    Integer insertCoursePlans(CoursePlan coursePlan);
    //批量删除
    Integer deleteCoursePlans(List<CoursePlanVO> planVOs);
    //成绩管理模糊查询
    /** 查询各个年级各个班级的课程 **/
    List<CoursePlan> selectScoreCoursePlans(@Param("clsName") String clsName, @Param("teaName") String teaName, @Param("courseName") String courseName);
    //根据学期更新课程状态
    Integer updateCourseStatusBySemesterId(Integer semesterId);
}
