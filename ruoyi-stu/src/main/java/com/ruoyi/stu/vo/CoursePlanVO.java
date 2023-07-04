package com.ruoyi.stu.vo;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.Semester;
import com.ruoyi.stu.domain.StuCls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePlanVO {
    private int planId;
    private Course course;
    private List<StuCls> stuCls;
    private Semester semester;
    private int status;
}
