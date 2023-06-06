package com.ruoyi.web.controller.stu;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stu.domain.Score;
import com.ruoyi.stu.domain.StuCoursePlan;
import com.ruoyi.stu.domain.StuInfo;
import com.ruoyi.stu.service.IStuCoursePlanService;
import com.ruoyi.stu.service.IStuScoreService;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stu/score")
public class ScoreController extends BaseController {

    @Autowired
    private IStuScoreService stuScoreService;

    @Autowired
    private IStuCoursePlanService stuCoursePlanService;

    /**
     * 查询已录入成绩的班级&老师&课程信息
     */
//    @GetMapping("/list")
//    public AjaxResult list(BiyeForm stuInfoMaterial) {
//        return AjaxResult.success(stuScoreService.selectFinshedCourse());
//
//    }

    /**
     * 查询已录入成绩的班级&老师&课程信息
     */
    @PreAuthorize("@ss.hasPermi('score:info:courseList')")
    @GetMapping("/list")
    public TableDataInfo list(String stuCls,String teaName, String courseName)
    {
        startPage();
        List<StuCoursePlan> list = stuCoursePlanService.findAll();
        return getDataTable(list);
    }
}
