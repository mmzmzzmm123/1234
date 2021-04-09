package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByDayflowassessment;
import com.ruoyi.project.benyi.service.IByDayFlowDetailService;
import com.ruoyi.project.benyi.service.IByDayflowassessmentService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.service.IByClassService;
import com.ruoyi.project.system.service.ISysDictDataService;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentplan;
import com.ruoyi.project.benyi.service.IByDayflowassessmentplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿园一日流程评估计划Controller
 *
 * @author tsbz
 * @date 2021-02-01
 */
@RestController
@RequestMapping("/benyi/dayflowassessmentplan")
public class ByDayflowassessmentplanController extends BaseController {
    @Autowired
    private IByDayflowassessmentplanService byDayflowassessmentplanService;
    @Autowired
    private IByDayflowassessmentService byDayflowassessmentService;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private ISysDictDataService sysDictDataService;
    @Autowired
    private IByDayFlowDetailService byDayFlowDetailService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询幼儿园一日流程评估计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayflowassessmentplan byDayflowassessmentplan) {
        byDayflowassessmentplan.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<ByDayflowassessmentplan> list = byDayflowassessmentplanService.selectByDayflowassessmentplanList(byDayflowassessmentplan);
        return getDataTable(list);
    }

    /**
     * 导出幼儿园一日流程评估计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:export')")
    @Log(title = "幼儿园一日流程评估计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayflowassessmentplan byDayflowassessmentplan) {
        List<ByDayflowassessmentplan> list = byDayflowassessmentplanService.selectByDayflowassessmentplanList(byDayflowassessmentplan);
        ExcelUtil<ByDayflowassessmentplan> util = new ExcelUtil<ByDayflowassessmentplan>(ByDayflowassessmentplan.class);
        return util.exportExcel(list, "dayflowassessmentplan");
    }

    /**
     * 获取幼儿园一日流程评估计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byDayflowassessmentplanService.selectByDayflowassessmentplanById(id));
    }

    /**
     * 新增幼儿园一日流程评估计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:add')")
    @Log(title = "幼儿园一日流程评估计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayflowassessmentplan byDayflowassessmentplan) {
        // 通过班级id 查询班级名称
        String classId = byDayflowassessmentplan.getClassid();
        // 通过内容id查询一日流程内容
        Long connentId = Long.parseLong(byDayflowassessmentplan.getConnent());
        // 通过学年学期查询学期名称
        String dict_type = "sys_xnxq";
        String dict_value = byDayflowassessmentplan.getXnxq();
        byDayflowassessmentplan.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        byDayflowassessmentplan.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayflowassessmentplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-" + sysDictDataService.selectDictLabel(dict_type, dict_value) + "-" + byDayFlowDetailService.selectByDayFlowDetailById(connentId).getName() + "-" + "评估计划");
        int iCount = byDayflowassessmentplanService.insertByDayflowassessmentplan(byDayflowassessmentplan);

        return toAjax(iCount);
    }

    /**
     * 修改幼儿园一日流程评估计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:edit')")
    @Log(title = "幼儿园一日流程评估计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayflowassessmentplan byDayflowassessmentplan) {
        // 通过班级id 查询班级名称
        String classId = byDayflowassessmentplan.getClassid();
        // 通过内容id查询一日流程内容
        Long connentId = Long.parseLong(byDayflowassessmentplan.getConnent());
        // 通过学年学期查询学期名称
        String dict_type = "sys_xnxq";
        String dict_value = byDayflowassessmentplan.getXnxq();
        byDayflowassessmentplan.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        byDayflowassessmentplan.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        byDayflowassessmentplan.setName(byClassService.selectByClassById(classId).getBjmc() + "-" + sysDictDataService.selectDictLabel(dict_type, dict_value) + "-" + byDayFlowDetailService.selectByDayFlowDetailById(connentId).getName() + "-" + "评估计划");
        return toAjax(byDayflowassessmentplanService.updateByDayflowassessmentplan(byDayflowassessmentplan));
    }

    /**
     * 删除幼儿园一日流程评估计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessmentplan:remove')")
    @Log(title = "幼儿园一日流程评估计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byDayflowassessmentplanService.deleteByDayflowassessmentplanByIds(ids));
    }
}













































