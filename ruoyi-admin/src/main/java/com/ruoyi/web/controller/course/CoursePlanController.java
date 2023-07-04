package com.ruoyi.web.controller.course;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.stu.domain.Course;
import com.ruoyi.stu.domain.CoursePlan;
import com.ruoyi.stu.service.ICoursePlanService;
import com.ruoyi.stu.service.impl.CourseServiceImpl;
import com.ruoyi.stu.vo.CoursePlanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/plan")
public class CoursePlanController extends BaseController{

    @Autowired
    private ICoursePlanService planService;

    @Anonymous
    @GetMapping("list")
    public TableDataInfo list(CoursePlan coursePlan,String courseName)
    {
        if(courseName != null) {
            Course course = new Course();
            course.setCourseName(courseName);
            coursePlan.setCourse(course);
        }
        List<CoursePlanVO> list = planService.selectCoursePlanVO(coursePlan);
        startPage();
        return getDataTable(list);
    }


    @Anonymous
    @Log(title = "添加课程安排", businessType = BusinessType.UPDATE)
    @PostMapping("adds")
    public AjaxResult adds(@RequestBody CoursePlan plan){
        return success(planService.insertCoursePlans(plan)>0?"添加成功！":"添加失败！");
    }
}
