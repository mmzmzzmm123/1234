package com.baoli.store.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
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
import com.baoli.store.domain.BaoliBizFeeRule;
import com.baoli.store.service.IBaoliBizFeeRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 费用规则Controller
 * 
 * @author niujs
 * @date 2024-04-17
 */
@RestController
@RequestMapping("/feeRule/feeRule")
public class BaoliBizFeeRuleController extends BaseController
{
    @Autowired
    private IBaoliBizFeeRuleService baoliBizFeeRuleService;

    /**
     * 查询费用规则列表
     */
    //PreAuthorize("@ss.hasPermi('feeRule:feeRule:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizFeeRule baoliBizFeeRule)
    {
        startPage();
        List<BaoliBizFeeRule> list = baoliBizFeeRuleService.selectBaoliBizFeeRuleList(baoliBizFeeRule);
        return getDataTable(list);
    }

    /**
     * 导出费用规则列表
     */
    @PreAuthorize("@ss.hasPermi('feeRule:feeRule:export')")
    @Log(title = "费用规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizFeeRule baoliBizFeeRule)
    {
        List<BaoliBizFeeRule> list = baoliBizFeeRuleService.selectBaoliBizFeeRuleList(baoliBizFeeRule);
        ExcelUtil<BaoliBizFeeRule> util = new ExcelUtil<BaoliBizFeeRule>(BaoliBizFeeRule.class);
        util.exportExcel(response, list, "费用规则数据");
    }

    /**
     * 获取费用规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('feeRule:feeRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizFeeRuleService.selectBaoliBizFeeRuleById(id));
    }

    /**
     * 新增费用规则
     */
    @PreAuthorize("@ss.hasPermi('feeRule:feeRule:add')")
    @Log(title = "费用规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizFeeRule baoliBizFeeRule)
    {
        return toAjax(baoliBizFeeRuleService.insertBaoliBizFeeRule(baoliBizFeeRule));
    }

    /**
     * 修改费用规则
     */
    @PreAuthorize("@ss.hasPermi('feeRule:feeRule:edit')")
    @Log(title = "费用规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizFeeRule baoliBizFeeRule)
    {
        return toAjax(baoliBizFeeRuleService.updateBaoliBizFeeRule(baoliBizFeeRule));
    }

    /**
     * 删除费用规则
     */
    @PreAuthorize("@ss.hasPermi('feeRule:feeRule:remove')")
    @Log(title = "费用规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizFeeRuleService.deleteBaoliBizFeeRuleByIds(ids));
    }
}
