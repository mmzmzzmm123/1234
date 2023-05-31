package com.ruoyi.stu.domain;

import lombok.Data;

@Data
public class Course {
    private Integer courseId;
    private Integer courseNo;
    private String courseName;
    private String courseType;
    private String coursePlan;
    private Integer courseExec;
    private Double courseScore;
}
