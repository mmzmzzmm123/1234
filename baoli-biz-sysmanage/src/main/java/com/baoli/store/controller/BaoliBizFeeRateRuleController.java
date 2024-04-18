package com.baoli.store.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.baoli.store.domain.BaoliBizFeeRateRule;
import com.baoli.store.service.IBaoliBizFeeRateRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 费率规则Controller
 * 
 * @author niujs
 * @date 2024-04-18
 */
@RestController
@RequestMapping("/feeRateRule/feeRateRule")
public class BaoliBizFeeRateRuleController extends BaseController
{
    @Autowired
    private IBaoliBizFeeRateRuleService baoliBizFeeRateRuleService;

    /**
     * 查询费率规则列表
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        startPage();
        List<BaoliBizFeeRateRule> list = baoliBizFeeRateRuleService.selectBaoliBizFeeRateRuleList(baoliBizFeeRateRule);
        return getDataTable(list);
    }

    /**
     * 导出费率规则列表
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:export')")
    @Log(title = "费率规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        List<BaoliBizFeeRateRule> list = baoliBizFeeRateRuleService.selectBaoliBizFeeRateRuleList(baoliBizFeeRateRule);
        ExcelUtil<BaoliBizFeeRateRule> util = new ExcelUtil<BaoliBizFeeRateRule>(BaoliBizFeeRateRule.class);
        util.exportExcel(response, list, "费率规则数据");
    }

    /**
     * 获取费率规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizFeeRateRuleService.selectBaoliBizFeeRateRuleById(id));
    }

    /**
     * 新增费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:add')")
    @Log(title = "费率规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        return toAjax(baoliBizFeeRateRuleService.insertBaoliBizFeeRateRule(baoliBizFeeRateRule));
    }

    /**
     * 修改费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:edit')")
    @Log(title = "费率规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        return toAjax(baoliBizFeeRateRuleService.updateBaoliBizFeeRateRule(baoliBizFeeRateRule));
    }

    /**
     * 删除费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:feeRateRule:remove')")
    @Log(title = "费率规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizFeeRateRuleService.deleteBaoliBizFeeRateRuleByIds(ids));
    }
}
