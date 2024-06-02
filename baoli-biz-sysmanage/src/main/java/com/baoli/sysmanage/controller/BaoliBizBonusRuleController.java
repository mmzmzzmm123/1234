package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BaoliBizBonusRule;
import com.baoli.sysmanage.service.IBaoliBizBonusRuleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 提成规则Controller
 * 
 * @author niujs
 * @date 2024-05-03
 */
@RestController
@RequestMapping("/sysmanage/bonusRule")
public class BaoliBizBonusRuleController extends BaseController
{
    @Autowired
    private IBaoliBizBonusRuleService baoliBizBonusRuleService;

    /**
     * 查询提成规则列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizBonusRule baoliBizBonusRule)
    {
        startPage();
        List<BaoliBizBonusRule> list = baoliBizBonusRuleService.selectBaoliBizBonusRuleList(baoliBizBonusRule);
        return getDataTable(list);
    }

    /**
     * 导出提成规则列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:export')")
    @Log(title = "提成规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizBonusRule baoliBizBonusRule)
    {
        List<BaoliBizBonusRule> list = baoliBizBonusRuleService.selectBaoliBizBonusRuleList(baoliBizBonusRule);
        ExcelUtil<BaoliBizBonusRule> util = new ExcelUtil<BaoliBizBonusRule>(BaoliBizBonusRule.class);
        util.exportExcel(response, list, "提成规则数据");
    }

    /**
     * 获取提成规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizBonusRuleService.selectBaoliBizBonusRuleById(id));
    }

    /**
     * 新增提成规则
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:add')")
    @Log(title = "提成规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizBonusRule baoliBizBonusRule)
    {
        return toAjax(baoliBizBonusRuleService.insertBaoliBizBonusRule(baoliBizBonusRule));
    }

    /**
     * 修改提成规则
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:edit')")
    @Log(title = "提成规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizBonusRule baoliBizBonusRule)
    {
        return toAjax(baoliBizBonusRuleService.updateBaoliBizBonusRule(baoliBizBonusRule));
    }

    /**
     * 删除提成规则
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusRule:remove')")
    @Log(title = "提成规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizBonusRuleService.deleteBaoliBizBonusRuleByIds(ids));
    }
}
