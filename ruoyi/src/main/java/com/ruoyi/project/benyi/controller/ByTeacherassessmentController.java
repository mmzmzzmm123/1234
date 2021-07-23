package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.project.benyi.domain.ByTeacherassessment;
import com.ruoyi.project.benyi.service.IByTeacherassessmentService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 教师月绩效考核Controller
 *
 * @author tsbz
 * @date 2021-07-01
 */
@RestController
@RequestMapping("/benyi/teacherassessment")
public class ByTeacherassessmentController extends BaseController {
    @Autowired
    private IByTeacherassessmentService byTeacherassessmentService;

    /**
     * 查询教师月绩效考核列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByTeacherassessment byTeacherassessment) {
        startPage();
        List<ByTeacherassessment> list = byTeacherassessmentService.selectByTeacherassessmentList(byTeacherassessment);
        return getDataTable(list);
    }

    /**
     * 导出教师月绩效考核列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:export')")
    @Log(title = "教师月绩效考核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByTeacherassessment byTeacherassessment) {
        List<ByTeacherassessment> list = byTeacherassessmentService.selectByTeacherassessmentList(byTeacherassessment);
        ExcelUtil<ByTeacherassessment> util = new ExcelUtil<ByTeacherassessment>(ByTeacherassessment.class);
        return util.exportExcel(list, "teacherassessment");
    }

    /**
     * 获取教师月绩效考核详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byTeacherassessmentService.selectByTeacherassessmentById(id));
    }

    /**
     * 新增教师月绩效考核
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:add')")
    @Log(title = "教师月绩效考核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByTeacherassessment byTeacherassessment) {

        //首先判断该教师当前月份是否已经被考核
        ByTeacherassessment byTeacherassessmentQuery = new ByTeacherassessment();
        byTeacherassessmentQuery.setJsid(byTeacherassessment.getJsid());
        byTeacherassessmentQuery.setMonth(byTeacherassessment.getMonth());
        List<ByTeacherassessment> list = byTeacherassessmentService.selectByTeacherassessmentList(byTeacherassessmentQuery);
        if (list != null && list.size() > 0) {
            return AjaxResult.error("该教师当前月份绩效考核以创建，无法重复创建。");
        } else {
            byTeacherassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
            Double total = (byTeacherassessment.getYrlcbl() * 0.6) + (byTeacherassessment.getJskqbl() * 0.1) + (byTeacherassessment.getYekqbl() * 0.1) + (byTeacherassessment.getSgbl() * 0.15) + (byTeacherassessment.getWsbl() * 0.05);
            byTeacherassessment.setZfbl(total);
            return toAjax(byTeacherassessmentService.insertByTeacherassessment(byTeacherassessment));
        }
    }

    /**
     * 修改教师月绩效考核
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:edit')")
    @Log(title = "教师月绩效考核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByTeacherassessment byTeacherassessment) {
        Double total = (byTeacherassessment.getYrlcbl() * 0.6) + (byTeacherassessment.getJskqbl() * 0.1) + (byTeacherassessment.getYekqbl() * 0.1) + (byTeacherassessment.getSgbl() * 0.15) + (byTeacherassessment.getWsbl() * 0.05);
        byTeacherassessment.setZfbl(total);
        return toAjax(byTeacherassessmentService.updateByTeacherassessment(byTeacherassessment));
    }

    /**
     * 删除教师月绩效考核
     */
    @PreAuthorize("@ss.hasPermi('benyi:teacherassessment:remove')")
    @Log(title = "教师月绩效考核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byTeacherassessmentService.deleteByTeacherassessmentByIds(ids));
    }
}
