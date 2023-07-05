package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.domain.StuCoursePlan;
import com.ruoyi.stu.domain.StuSemester;
import com.ruoyi.stu.mapper.StuClsMapper;
import com.ruoyi.stu.mapper.StuCoursePlanMapper;
import com.ruoyi.stu.service.IStuCoursePlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StuCoursePlanServiceImpl implements IStuCoursePlanService{
    @Autowired
    private StuCoursePlanMapper stuCoursePlanMapper;

    @Autowired
    private StuClsMapper clsMapper;
    
    public List<StuCoursePlan> findAll(String stuCls,String teaName, String courseName){
        return stuCoursePlanMapper.findAll( stuCls, teaName,  courseName);
    }

    @Override
    public List<StuCoursePlan> findAllLevelCourse(String clsYear, String courseName,String semesterName) {
        return stuCoursePlanMapper.findAllLevelCourse(clsYear,courseName,semesterName);
    }

    @Override
    public Integer addCoursePlans(Integer semesterId,String clsYear,Integer courseId) {
        System.out.println(semesterId+","+clsYear+","+courseId);
        StuCls stuCls = new StuCls();
        stuCls.setClsYear(clsYear);
        List<StuCls> clsList = clsMapper.selectStuClsList(stuCls);
        ArrayList<StuCoursePlan> stuCoursePlans = new ArrayList<>();
        for (StuCls cls: clsList
             ) {
            StuCoursePlan stuCoursePlan = new StuCoursePlan();
            stuCoursePlan.setCourseId(courseId);
            stuCoursePlan.setSemesterId(semesterId);
            stuCoursePlan.setClsId(cls.getClsId());
            stuCoursePlans.add(stuCoursePlan);
        }
        return stuCoursePlanMapper.addCoursePlans(stuCoursePlans);
    }

    @Override
    public List<StuSemester> findAllSemester() {
        return stuCoursePlanMapper.findAllSemester();
    }

    @Override
    public List<Course> findAllCourses() {
        return stuCoursePlanMapper.findAllCourses();
    }
}
