package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.StuCoursePlan;
import com.ruoyi.stu.mapper.StuCoursePlanMapper;
import com.ruoyi.stu.service.IStuCoursePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuCoursePlanServiceImpl implements IStuCoursePlanService{
    @Autowired
    private StuCoursePlanMapper stuCoursePlanMapper;
    
    public List<StuCoursePlan> findAll(String stuCls,String teaName, String courseName){
        return stuCoursePlanMapper.findAll( stuCls, teaName,  courseName);
    }

    @Override
    public List<StuCoursePlan> findAllLevelCourse(String clsYear, String courseName) {
        return stuCoursePlanMapper.findAllLevelCourse(clsYear,courseName);
    }
}
