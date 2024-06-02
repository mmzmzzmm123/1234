package com.baoli.company.controller;

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
import com.baoli.company.domain.BaoliBizCompanyFeeRateRule;
import com.baoli.company.service.IBaoliBizCompanyFeeRateRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 对司费率规则Controller
 * 
 * @author niujs
 * @date 2024-05-10
 */
@RestController
@RequestMapping("/feeRateRule/companyFeeRateRule")
public class BaoliBizCompanyFeeRateRuleController extends BaseController
{
    @Autowired
    private IBaoliBizCompanyFeeRateRuleService baoliBizCompanyFeeRateRuleService;

    /**
     * 查询对司费率规则列表
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        startPage();
        List<BaoliBizCompanyFeeRateRule> list = baoliBizCompanyFeeRateRuleService.selectBaoliBizCompanyFeeRateRuleList(baoliBizCompanyFeeRateRule);
        return getDataTable(list);
    }

    /**
     * 导出对司费率规则列表
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:export')")
    @Log(title = "对司费率规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        List<BaoliBizCompanyFeeRateRule> list = baoliBizCompanyFeeRateRuleService.selectBaoliBizCompanyFeeRateRuleList(baoliBizCompanyFeeRateRule);
        ExcelUtil<BaoliBizCompanyFeeRateRule> util = new ExcelUtil<BaoliBizCompanyFeeRateRule>(BaoliBizCompanyFeeRateRule.class);
        util.exportExcel(response, list, "对司费率规则数据");
    }

    /**
     * 获取对司费率规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCompanyFeeRateRuleService.selectBaoliBizCompanyFeeRateRuleById(id));
    }

    /**
     * 新增对司费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:add')")
    @Log(title = "对司费率规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        return toAjax(baoliBizCompanyFeeRateRuleService.insertBaoliBizCompanyFeeRateRule(baoliBizCompanyFeeRateRule));
    }

    /**
     * 修改对司费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:edit')")
    @Log(title = "对司费率规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        return toAjax(baoliBizCompanyFeeRateRuleService.updateBaoliBizCompanyFeeRateRule(baoliBizCompanyFeeRateRule));
    }

    /**
     * 删除对司费率规则
     */
    @PreAuthorize("@ss.hasPermi('feeRateRule:companyFeeRateRule:remove')")
    @Log(title = "对司费率规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCompanyFeeRateRuleService.deleteBaoliBizCompanyFeeRateRuleByIds(ids));
    }
}
