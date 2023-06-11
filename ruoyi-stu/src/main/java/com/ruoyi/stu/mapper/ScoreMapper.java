package com.ruoyi.stu.mapper;

import com.ruoyi.stu.domain.Score;
import com.ruoyi.stu.domain.StuCls;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成绩参数Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public interface ScoreMapper
{
    List<Score> selectFinshedCourse(@Param("stuCls") String stuCls,@Param("teacherName") String teacherName,@Param("courseName") String courseName);
    //获取某班某门课程成绩
    List<Score> selectCourseScore(@Param("stuCls")String stuCls,@Param("courseName")String courseName);
    //获取某班学生历史平均成绩
    List<Score> selectHistoryScore(@Param("stuCls")String stuCls);
    //批量更新某班学生平时成绩
    Integer updateClassDailyScore(@Param("snos") List<Score> snos);
}
