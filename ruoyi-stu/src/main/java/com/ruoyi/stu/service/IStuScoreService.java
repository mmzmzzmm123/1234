package com.ruoyi.stu.service;

import com.ruoyi.stu.domain.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IStuScoreService {
    List<Score> selectFinshedCourse(String stuCls,String teacherName,String courseName);
    List<Score> selectCourseScore(String stuCls,String courseName);
    //获取某班学生历史平均成绩
    List<Score> selectHistoryScore(String stuCls);
    //批量更新某班学生平时成绩
    Integer updateClassDailyScore(@Param("snos") List<Score> snos);
}
