package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.Score;
import com.ruoyi.stu.mapper.ScoreMapper;
import com.ruoyi.stu.service.IStuScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StuScoreServiceImpl implements IStuScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public List<Score> selectFinshedCourse(String stuCls,String teacherName,String courseName) {
        return scoreMapper.selectFinshedCourse(stuCls,teacherName,courseName);
    }
}
