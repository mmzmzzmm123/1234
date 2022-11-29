package com.ruoyi.student.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.consts.StudentConstant;
import com.ruoyi.student.domain.StuAccVo;
import com.ruoyi.student.domain.StuInfo;
import com.ruoyi.student.service.IStuAccService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生统计Controller
 *
 * @author wangzq
 * @date 2022-11-22
 */
@RestController
@RequestMapping("/student/account")
public class StuAccController extends BaseController {

    @Autowired
    private IStuAccService stuAccService;

    /**
     * 统计学生信息（按学院分组）
     */
    @GetMapping("/list")
    public TableDataInfo list(StuAccVo stuAccVo)
    {
        startPage();
        List<StuAccVo> list = stuAccService.selectStuAccList(stuAccVo);
        return getDataTable(list);
    }

    /**
     * 统计学生信息（汇总）
     */
    @GetMapping("/sumList")
    public AjaxResult sumList(StuAccVo stuAccVo)
    {
        List<StuAccVo> list = stuAccService.selectStuAccSumList(stuAccVo);
        return AjaxResult.success(list);
    }

    /**
     * 导出学生信息列表
     */
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response)
    {
        StuAccVo stuAccVo = new StuAccVo();
        stuAccVo.setDivideCollege("Y");
        // 雁塔校区
        stuAccVo.setCampus(StudentConstant.CAMPUS_YAN_TA);
        List<StuAccVo> yantaList = stuAccService.selectStuAccList(stuAccVo);
        for (StuAccVo stu : yantaList) {
            stu.setCampusName("雁塔校区");
        }
        // 长安校区
        stuAccVo.setCampus(StudentConstant.CAMPUS_CHANG_AN);
        List<StuAccVo> changanList = stuAccService.selectStuAccList(stuAccVo);
        for (StuAccVo stu : changanList) {
            stu.setCampusName("长安校区");
        }
        // 两个校区
        List<StuAccVo> campList = stuAccService.selectCampStuAccSumList(stuAccVo);
        StuAccVo accSum = stuAccService.selectStuAccSum(stuAccVo);
        accSum.setCampusName("总计");
        campList.add(accSum);

        StuAccVo stuAccVo1 = campList.get(0);
        stuAccVo1.setDeptName("总计");
        StuAccVo stuAccVo2 = campList.get(1);
        stuAccVo2.setDeptName("总计");
        yantaList.add(stuAccVo1);
        changanList.add(stuAccVo2);

        Map<String, List<StuAccVo>> map = new HashMap<>();
        map.put("雁塔校区", yantaList);
        map.put("长安校区", changanList);
        map.put("汇总", campList);
        ExcelUtil<StuAccVo> util = new ExcelUtil<StuAccVo>(StuAccVo.class);
        util.exportEasyExcel(response, map, "学生台账信息数据");
    }
}
