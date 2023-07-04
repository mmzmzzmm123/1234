package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.CoursePlan;

import java.util.List;

public interface CoursePlanMapper {
    List<CoursePlan> selectCoursePlan(CoursePlan coursePlan);
    //批量添加
    Integer insertCoursePlans(CoursePlan coursePlan);
    //计数
    Integer selectForInsert(CoursePlan coursePlan);
}
