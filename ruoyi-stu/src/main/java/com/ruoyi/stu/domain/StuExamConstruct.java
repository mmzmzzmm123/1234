package com.ruoyi.stu.domain;

import lombok.Data;

@Data
public class StuExamConstruct {
    private Integer examId;
//    private Integer clsId;
    private StuSemester semester;
    private Integer courseId;
    private Course course;
    private Integer semesterId;
    private String examTitles;
    private String examPoints;
    private String examPriorities;
}
