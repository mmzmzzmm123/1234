package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jjpt.business.domain.dto.ExamSaveReqDto;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.jjpt.business.domain.ElExam;
import com.jjpt.business.service.IElExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试管理Controller
 * 
 * @author ruoyi
 * @date 2024-05-18
 */
@RestController
@RequestMapping("/exammange/exam")
public class ElExamController extends BaseController
{
    @Autowired
    private IElExamService elExamService;

    /**
     * 查询考试管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElExam elExam)
    {
        startPage();
        List<ElExam> list = elExamService.selectElExamList(elExam);
        return getDataTable(list);
    }

    /**
     * 导出考试管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:export')")
    @Log(title = "考试管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ElExam elExam)
    {
        List<ElExam> list = elExamService.selectElExamList(elExam);
        ExcelUtil<ElExam> util = new ExcelUtil<ElExam>(ElExam.class);
        util.exportExcel(response, list, "考试管理数据");
    }

    /**
     * 获取考试管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elExamService.selectElExamById(id));
    }

    /**
     * 新增考试管理
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:add')")
    @Log(title = "考试管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExamSaveReqDto elExam)
    {
        return toAjax(elExamService.insertElExam(elExam));
    }

    /**
     * 修改考试管理
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:edit')")
    @Log(title = "考试管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExamSaveReqDto elExam)
    {
        return toAjax(elExamService.insertElExam(elExam));
    }

    /**
     * 删除考试管理
     */
    @PreAuthorize("@ss.hasPermi('exammange:exam:remove')")
    @Log(title = "考试管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elExamService.deleteElExamByIds(ids));
    }
}
