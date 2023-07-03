package com.ruoyi.web.controller.stu;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.stu.domain.*;
import com.ruoyi.stu.mapper.StuExamConstructMapper;
import com.ruoyi.stu.service.IStuCoursePlanService;
import com.ruoyi.stu.service.IStuExamConstructService;
import com.ruoyi.stu.service.IStuScoreService;
import com.ruoyi.stu.vo.BiyeForm;
import com.ruoyi.stu.vo.StuInfoMaterial;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stu/score")
public class ScoreController extends BaseController {

    @Autowired
    private IStuScoreService stuScoreService;

    @Autowired
    private IStuCoursePlanService stuCoursePlanService;

    @Autowired
    private IStuExamConstructService examConstructService;

    /**
     * 查询已录入成绩的班级&老师&课程信息
     */
    @PreAuthorize("@ss.hasPermi('score:info:courseList')")
    @GetMapping("/list")
    public TableDataInfo list(String stuCls,String teaName, String courseName)
    {
        startPage();
        List<StuCoursePlan> list = stuCoursePlanService.findAll(stuCls, teaName,  courseName);
        return getDataTable(list);
    }
    /**
     *  查询各个年级的课程
     */
    @PreAuthorize("@ss.hasPermi('courseplan:info:courseplanList')")
    @GetMapping("/coursePlanList")
    public TableDataInfo courseplanlist(String clsYear,String courseName,String semesterName)
    {
        startPage();
        List<StuCoursePlan> list = stuCoursePlanService.findAllLevelCourse(clsYear,courseName,semesterName);
        return getDataTable(list);
    }
    /**
     *  添加课程计划
     */
    @PreAuthorize("@ss.hasPermi('courseplan:info:courseplanList')")
    @PostMapping("/addCoursePlans")
    public AjaxResult addCoursePlans(Integer semesterId,String clsYear,Integer courseId)
    {
        return toAjax(stuCoursePlanService.addCoursePlans( semesterId, clsYear, courseId));
    }
    /**
     * 查询某个班级的成绩
     */
    @PreAuthorize("@ss.hasPermi('score:info:courseList')")
    @GetMapping("/courseScore")
    public TableDataInfo courseScore(String stuCls, String courseName)
    {
        startPage();
        List<Score> list = stuScoreService.selectCourseScore(stuCls,courseName);
        return getDataTable(list);
    }

    /**
     * 查询某个班级学生的平均成绩
     */
    @PreAuthorize("@ss.hasPermi('score:info:courseList')")
    @GetMapping("/hisScoure")
    public AjaxResult clsStuHisScore(String stuCls)
    {
        List<Score> list = stuScoreService.selectHistoryScore(stuCls);
        return success(list);
    }

    /**
     * 查询所有学期
     */
    @PreAuthorize("@ss.hasPermi('semester:info:semesterList')")
    @GetMapping("/allSemester")
    public AjaxResult allSemester()
    {
        return success(stuCoursePlanService.findAllSemester());
    }
    /**
     * 查询所有课程
     */
    @PreAuthorize("@ss.hasPermi('semester:info:semesterList')")
    @GetMapping("/allCourses")
    public AjaxResult allCourse()
    {
        return success(stuCoursePlanService.findAllCourses());
    }
    /**
     * 查询某个班级学生的平均成绩
     */
    @PreAuthorize("@ss.hasPermi('score:info:courseList')")
    @Log(title = "成绩录入", businessType = BusinessType.UPDATE)
    @PostMapping("/setScore")
    public AjaxResult edit(@RequestBody List<Score> scores)
    {
        return toAjax(stuScoreService.updateClassDailyScore(scores));
    }

    /**
     * 学生成绩导出
     */
    @PreAuthorize("@ss.hasPermi('score:info:export')")
    @Log(title = "成绩导出", businessType = BusinessType.UPDATE)
    @PostMapping("/export")
    public void export(HttpServletResponse response,String stuCls, String courseName)
    {
        List<Score> list = stuScoreService.selectCourseScore(stuCls,courseName);
        ExcelUtil<Score> util = new ExcelUtil<Score>(Score.class);
        util.exportExcel(response, list, "提交材料参数数据");
    }

    /**
     * 导出实践评分表
     */
//    @PreAuthorize("@ss.hasPermi('score:info:export')")
    @Log(title = "实践评分表导出", businessType = BusinessType.UPDATE)
    @PostMapping("/shijianExport")
    public void shijianExport(HttpServletResponse response,String stuCls, Integer courseId,Integer semesterId,String courseName)
    {

        //结构
        StuExamConstruct construct = examConstructService.findByIds(courseId, semesterId);
        //分数
        List<Score> scores = stuScoreService.selectCourseScore(stuCls, courseName);
        List<String> fields = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            fields.add(i+"");
        }
        ExcelUtil util = new ExcelUtil(Score.class);
//        util.exportDIYExcel(response,construct);
//        util.exportDIYExcel(response,null,"s1","123",null);
//        util.exportExcel(response,scores,"s1","123");
//        List<Score> list = stuScoreService.selectCourseScore(stuCls,courseName);
//        ExcelUtil<Score> util = new ExcelUtil<Score>(Score.class);
//        util.exportExcel(response, list, "提交材料参数数据");
    }
}
