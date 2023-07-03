package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.Semester;

import java.util.List;

public interface SemesterMapper {
    List<Semester> selectSemester(Semester semester);
    Integer addSemester(Semester semester);
}
