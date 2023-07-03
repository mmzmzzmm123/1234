package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.Semester;
import com.ruoyi.stu.mapper.SemesterMapper;
import com.ruoyi.stu.service.ISemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SemesterServiceImpl implements ISemesterService {

    @Autowired
    private SemesterMapper semesterMapper;

    @Override
    public List<Semester> selectSemester(Semester semester) {
        return semesterMapper.selectSemester(semester);
    }

    @Override
    public Integer addSemester(Semester semester) {
        return semesterMapper.addSemester(semester);
    }
}
