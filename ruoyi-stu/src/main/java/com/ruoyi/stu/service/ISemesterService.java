package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Semester;

import java.util.List;

public interface ISemesterService {
    List<Semester> selectSemester(Semester semester);
    Integer addSemester(Semester semester);
}
