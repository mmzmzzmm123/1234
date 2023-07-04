package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.CoursePlan;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.vo.CoursePlanVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICoursePlanService {
    List<CoursePlan> selectCoursePlan(CoursePlan coursePlan);
    List<CoursePlanVO> selectCoursePlanVO(CoursePlan coursePlan);
    //批量添加
    Integer insertCoursePlans(CoursePlan coursePlan);
}
