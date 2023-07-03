package com.ruoyi.stu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private Integer courseId;
    private String courseNo;
    private String courseName;
    private String courseType;
    private String coursePlan;
    private Integer courseExec;
    private Double courseScore;
}
