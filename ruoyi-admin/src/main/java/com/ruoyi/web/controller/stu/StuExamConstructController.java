package com.ruoyi.web.controller.stu;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stu.domain.Score;
import com.ruoyi.stu.domain.StuExamConstruct;
import com.ruoyi.stu.service.IStuExamConstructService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stu/exam")
public class StuExamConstructController extends BaseController {

    @Autowired
    private IStuExamConstructService constructService;
    /**
     * 查询某个学期某门课程的试卷结构
     */
    @PreAuthorize("@ss.hasPermi('exam:info:construct')")
    @GetMapping("/construct")
    public AjaxResult construct(Integer courseId,Integer semesterId)
    {
        return success(constructService.findByIds(courseId,semesterId));
    }

    /**
     * 添加某个学期某门课程的试卷结构
     */
    @PreAuthorize("@ss.hasPermi('exam:info:construct')")
    @GetMapping("/addConstruct")
    public AjaxResult addConstruct(StuExamConstruct construct)
    {
        return toAjax(constructService.addExamConstruct(construct));
    }


    /**
     * 修改某个学期某门课程的试卷结构
     */
    @PreAuthorize("@ss.hasPermi('exam:info:construct')")
    @GetMapping("/updateConstruct")
    public AjaxResult updateConstruct(StuExamConstruct construct)
    {
        return toAjax(constructService.updateExamConstruct(construct));
    }
}