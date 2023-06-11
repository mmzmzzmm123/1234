package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.StuCoursePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuCoursePlanMapper {
    List<StuCoursePlan> findAll(@Param("stuCls") String stuCls,@Param("teaName") String teaName,@Param("courseName") String courseName);
}
