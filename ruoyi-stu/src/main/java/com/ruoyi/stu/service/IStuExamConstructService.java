package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.StuExamConstruct;
import com.ruoyi.stu.mapper.StuExamConstructMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStuExamConstructService {
    //查询试卷结构
    StuExamConstruct findByIds(Integer courseId, Integer semesterId);
    //添加试卷结构
    Integer addExamConstruct(StuExamConstruct construct);
    //修改试卷结构
    Integer updateExamConstruct(StuExamConstruct construct);
}
