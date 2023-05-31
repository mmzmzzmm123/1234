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

}
