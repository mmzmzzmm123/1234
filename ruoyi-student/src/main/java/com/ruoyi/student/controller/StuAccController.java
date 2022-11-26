package com.ruoyi.student.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.student.domain.StuAccVo;
import com.ruoyi.student.domain.StuInfo;
import com.ruoyi.student.service.IStuAccService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
//    @PreAuthorize("@ss.hasPermi('student:info:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuAccVo stuAccVo)
    {
        List<StuAccVo> list = stuAccService.selectStuAccList(stuAccVo);
        ExcelUtil<StuAccVo> util = new ExcelUtil<StuAccVo>(StuAccVo.class);
        util.exportExcel(response, list, "学生台账信息数据");
    }
}
