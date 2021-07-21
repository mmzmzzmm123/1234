package com.stdiet.web.controller.custom;

import java.util.List;

import com.stdiet.common.utils.HealthyUtils;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysPreSaleSurvey;
import com.stdiet.custom.service.ISysPreSaleSurveyService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 简易问卷调查Controller
 *
 * @author xzj
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/custom/preSaleSurvey")
public class SysPreSaleSurveyController extends BaseController
{
    @Autowired
    private ISysPreSaleSurveyService sysPreSaleSurveyService;

    /**
     * 查询简易问卷调查列表
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPreSaleSurvey sysPreSaleSurvey)
    {
        startPage();
        sysPreSaleSurvey.setQuestionType(1);
        List<SysPreSaleSurvey> list = sysPreSaleSurveyService.selectSysPreSaleSurveyList(sysPreSaleSurvey);
        if(list != null && list.size() > 0){
            for (SysPreSaleSurvey survey : list) {
                //计算标准体重
                survey.setStandardWeight(HealthyUtils.calculateStandardWeightByBMI(survey.getTall(), survey.getAge(), survey.getSex()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出简易问卷调查列表
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:export')")
    @Log(title = "简易问卷调查", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysPreSaleSurvey sysPreSaleSurvey)
    {
        List<SysPreSaleSurvey> list = sysPreSaleSurveyService.selectSysPreSaleSurveyList(sysPreSaleSurvey);
        ExcelUtil<SysPreSaleSurvey> util = new ExcelUtil<SysPreSaleSurvey>(SysPreSaleSurvey.class);
        return util.exportExcel(list, "preSaleSurvey");
    }

    /**
     * 获取简易问卷调查详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysPreSaleSurveyService.selectSysPreSaleSurveyById(id));
    }

    /**
     * 新增简易问卷调查
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:add')")
    @Log(title = "简易问卷调查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysPreSaleSurvey sysPreSaleSurvey)
    {
        return toAjax(sysPreSaleSurveyService.insertSysPreSaleSurvey(sysPreSaleSurvey));
    }

    /**
     * 修改简易问卷调查
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:edit')")
    @Log(title = "简易问卷调查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysPreSaleSurvey sysPreSaleSurvey)
    {
        return toAjax(sysPreSaleSurveyService.updateSysPreSaleSurvey(sysPreSaleSurvey));
    }

    /**
     * 删除简易问卷调查
     */
    @PreAuthorize("@ss.hasPermi('custom:preSaleSurvey:remove')")
    @Log(title = "简易问卷调查", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysPreSaleSurveyService.deleteSysPreSaleSurveyByIds(ids));
    }
}