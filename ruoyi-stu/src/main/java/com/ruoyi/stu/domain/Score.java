package com.ruoyi.stu.domain;

import lombok.Data;

@Data
public class Score {
    private Integer id;
    private StuInfo stu;
    private Teacher teacher;
    private Course course;
    private Integer dailyScore;
    private Integer finallyScore;
    private Integer avgScore;
}
