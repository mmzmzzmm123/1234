package com.ruoyi.stu.service.impl;

import com.ruoyi.stu.domain.CoursePlan;
import com.ruoyi.stu.domain.StuCls;
import com.ruoyi.stu.mapper.CoursePlanMapper;
import com.ruoyi.stu.service.ICoursePlanService;
import com.ruoyi.stu.vo.CoursePlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class CoursePlanServiceImpl implements ICoursePlanService {

    @Autowired
    private CoursePlanMapper coursePlanMapper;

    @Override
    public List<CoursePlanVO> selectCoursePlanVO(CoursePlan coursePlan) {
        List<CoursePlan> findList = coursePlanMapper.selectCoursePlan(coursePlan);
        List<CoursePlanVO> resultList = new ArrayList<>();
        for (CoursePlan plan: findList
             ) {
            boolean flag = true;
            for (CoursePlanVO planVO: resultList
                 ) {
                if(plan.equalsVO(planVO)){
                    planVO.getStuCls().add(plan.getStuCls());
                    flag = false;
                    break;
                }
            }
            if(flag) {
                CoursePlanVO planVO = new CoursePlanVO();
                planVO.setPlanId(plan.getPlanId());
                planVO.setCourse(plan.getCourse());
                planVO.setStatus(plan.getStatus());
                planVO.setSemester(plan.getSemester());
                ArrayList<StuCls> stuCls = new ArrayList<>();
                stuCls.add(plan.getStuCls());
                planVO.setStuCls(stuCls);
                resultList.add(planVO);
            }
        }
        return resultList;
    }
    //批量添加
    @Transactional
    @Override
    public Integer insertCoursePlans(CoursePlan coursePlan) {
        return coursePlanMapper.selectForInsert(coursePlan)>0 ? 0 : coursePlanMapper.insertCoursePlans(coursePlan);
    }

    @Override
    public Integer deleteCoursePlans(List<CoursePlanVO> planVOs) {
        return coursePlanMapper.deleteCoursePlans(planVOs);
    }

    @Override
    public List<CoursePlan> selectScoreCoursePlans(String clsName, String teaName, String courseName) {
        return coursePlanMapper.selectScoreCoursePlans(clsName,teaName,courseName);
    }

    @Override
    public Integer updateCourseStatusBySemesterId(Integer semesterId) {
        return coursePlanMapper.updateCourseStatusBySemesterId(semesterId);
    }

    @Override
    public List<CoursePlan> selectCoursePlan(CoursePlan coursePlan) {
        return coursePlanMapper.selectCoursePlan(coursePlan);
    }
}
