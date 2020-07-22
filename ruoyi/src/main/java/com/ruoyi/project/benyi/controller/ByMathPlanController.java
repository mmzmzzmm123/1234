package com.ruoyi.project.benyi.controller;

import java.util.Date;
import java.util.List;

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
import com.ruoyi.project.benyi.domain.ByMathPlan;
import com.ruoyi.project.benyi.service.IByMathPlanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 游戏数学方案Controller
 *
 * @author tsbz
 * @date 2020-07-21
 */
@RestController
@RequestMapping("/benyi/plan")
public class ByMathPlanController extends BaseController
{
    @Autowired
    private IByMathPlanService byMathPlanService;

    @Autowired
    private ISysDictDataService dictDataService;

/**
 * 查询游戏数学方案列表
 */
@PreAuthorize("@ss.hasPermi('benyi:plan:list')")
@GetMapping("/list")
        public TableDataInfo list(ByMathPlan byMathPlan)
    {
        startPage();
        List<ByMathPlan> list = byMathPlanService.selectByMathPlanList(byMathPlan);
        return getDataTable(list);
    }
    
    /**
     * 导出游戏数学方案列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:plan:export')")
    @Log(title = "游戏数学方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByMathPlan byMathPlan)
    {
        List<ByMathPlan> list = byMathPlanService.selectByMathPlanList(byMathPlan);
        ExcelUtil<ByMathPlan> util = new ExcelUtil<ByMathPlan>(ByMathPlan.class);
        return util.exportExcel(list, "plan");
    }

    /**
     * 获取游戏数学方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:plan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        AjaxResult ajax = AjaxResult.success();
        ByMathPlan byMathPlan = byMathPlanService.selectByMathPlanById(id);
        // 将数据库中的type类型分割  然后返回给前端
        String dictType = "sys_math_type";
        ajax.put("types", dictDataService.selectDictDataByType(dictType));
        String strType = byMathPlan.getType();
        ajax.put("typeIds", strType.split(";"));

        ajax.put(AjaxResult.DATA_TAG, byMathPlan);
        return ajax;
    }

    /**
     * 新增游戏数学方案
     */
    @PreAuthorize("@ss.hasPermi('benyi:plan:add')")
    @Log(title = "游戏数学方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByMathPlan byMathPlan)
    {
        byMathPlan.setCreatetime(new Date());
        return toAjax(byMathPlanService.insertByMathPlan(byMathPlan));
    }

    /**
     * 修改游戏数学方案
     */
    @PreAuthorize("@ss.hasPermi('benyi:plan:edit')")
    @Log(title = "游戏数学方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByMathPlan byMathPlan)
    {
        return toAjax(byMathPlanService.updateByMathPlan(byMathPlan));
    }

    /**
     * 删除游戏数学方案
     */
    @PreAuthorize("@ss.hasPermi('benyi:plan:remove')")
    @Log(title = "游戏数学方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(byMathPlanService.deleteByMathPlanByIds(ids));
    }
}
