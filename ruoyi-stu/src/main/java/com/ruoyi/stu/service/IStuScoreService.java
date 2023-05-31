package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStuScoreService {
    List<Score> selectFinshedCourse(String stuCls,String teacherName,String courseName);
}
