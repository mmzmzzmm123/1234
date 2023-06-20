package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.StuExamConstruct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuExamConstructMapper {
    //通过学期和课程查询试卷结构
    List<StuExamConstructMapper> findCon(@Param("courseId")Integer courseId, @Param("semesterId")Integer semesterId);
    //添加试卷结构
    Integer addExamConstruct(StuExamConstruct construct);
    //修改试卷结构
    Integer updateExamConstruct(StuExamConstruct construct);
}
