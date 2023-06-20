package com.ruoyi.stu.domain;

import lombok.Data;

import java.util.List;

@Data
public class StuCoursePlan {
    private int planId;
    private Course course;
    private Long clsId;
    private Integer courseId;
    private Integer semesterId;
    private StuCls stuCls;
    private StuSemester semester;
    private int status;
}
