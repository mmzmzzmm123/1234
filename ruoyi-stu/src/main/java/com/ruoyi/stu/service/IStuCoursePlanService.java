package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.StuCoursePlan;

import java.util.List;

public interface IStuCoursePlanService {
    List<StuCoursePlan> findAll(String stuCls,String teaName, String courseName);
}
