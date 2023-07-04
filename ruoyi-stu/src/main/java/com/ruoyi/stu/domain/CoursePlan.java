package com.ruoyi.stu.domain;

import com.ruoyi.stu.vo.CoursePlanVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePlan {
    private int planId;
    private Long clsId;
    private Integer courseId;
    private Integer semesterId;
    private int status;
    private Integer[] stuClss;
    private Course course;
    private StuCls stuCls;
    private Semester semester;
    public boolean equalsVO(CoursePlanVO plan){
        return plan.getCourse().getCourseId() == courseId && plan.getSemester().getSemesterId()==semesterId ? true :false;
    }
}
