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
    
    public List<StuCoursePlan> findAll(){
        return stuCoursePlanMapper.findAll();
    }
}
