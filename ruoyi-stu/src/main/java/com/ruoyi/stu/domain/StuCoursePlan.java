package com.ruoyi.stu.domain;

import lombok.Data;

@Data
public class StuCoursePlan {
    private int planId;
    private Course course;
    private StuCls stuCls;
    private StuSemester semester;
    private int status;
}
