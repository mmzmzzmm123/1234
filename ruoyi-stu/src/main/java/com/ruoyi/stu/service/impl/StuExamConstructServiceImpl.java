package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.StuExamConstruct;
import com.ruoyi.stu.mapper.StuExamConstructMapper;
import com.ruoyi.stu.service.IStuExamConstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuExamConstructServiceImpl implements IStuExamConstructService {

    @Autowired
    private StuExamConstructMapper constructMapper;

    @Override
    public StuExamConstruct findByIds(Integer courseId, Integer semesterId) {
        return constructMapper.findCon(courseId,semesterId);
    }

    @Override
    public Integer addExamConstruct(StuExamConstruct construct) {
        return constructMapper.addExamConstruct(construct);
    }

    @Override
    public Integer updateExamConstruct(StuExamConstruct construct) {
        return constructMapper.updateExamConstruct(construct);
    }
}
