package com.ruoyi.stu.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excels;
import lombok.Data;

@Data
public class Score {
    private Integer id;
    private Integer stuId;
    private Integer courseId;
    @Excels({
           @Excel(name = "学号",targetAttr = "stuNo"),
           @Excel(name = "姓名",targetAttr = "stuName")
    })
    private StuInfo student;
    private Teacher teacher;
    private Course course;
    @Excel(name = "平时成绩")
    private Integer dailyScore;
    @Excel(name =  "考试成绩")
    private Integer finallyScore;
    private Integer avgScore;
}
