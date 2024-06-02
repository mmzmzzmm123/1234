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
import com.baoli.sysmanage.domain.BaoliBizBonusRuleParam;
import com.baoli.sysmanage.service.IBaoliBizBonusRuleParamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 提成规则参数Controller
 * 
 * @author niujs
 * @date 2024-05-03
 */
@RestController
@RequestMapping("/sysmanage/bonusParam")
public class BaoliBizBonusRuleParamController extends BaseController
{
    @Autowired
    private IBaoliBizBonusRuleParamService baoliBizBonusRuleParamService;

    /**
     * 查询提成规则参数列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        if(baoliBizBonusRuleParam.isPageAble()){
            startPage();
        }
        List<BaoliBizBonusRuleParam> list = baoliBizBonusRuleParamService.selectBaoliBizBonusRuleParamList(baoliBizBonusRuleParam);
        return getDataTable(list);
    }

    /**
     * 导出提成规则参数列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:export')")
    @Log(title = "提成规则参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        List<BaoliBizBonusRuleParam> list = baoliBizBonusRuleParamService.selectBaoliBizBonusRuleParamList(baoliBizBonusRuleParam);
        ExcelUtil<BaoliBizBonusRuleParam> util = new ExcelUtil<BaoliBizBonusRuleParam>(BaoliBizBonusRuleParam.class);
        util.exportExcel(response, list, "提成规则参数数据");
    }

    /**
     * 获取提成规则参数详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizBonusRuleParamService.selectBaoliBizBonusRuleParamById(id));
    }

    /**
     * 新增提成规则参数
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:add')")
    @Log(title = "提成规则参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        return toAjax(baoliBizBonusRuleParamService.insertBaoliBizBonusRuleParam(baoliBizBonusRuleParam));
    }

    /**
     * 修改提成规则参数
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:edit')")
    @Log(title = "提成规则参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        return toAjax(baoliBizBonusRuleParamService.updateBaoliBizBonusRuleParam(baoliBizBonusRuleParam));
    }

    /**
     * 删除提成规则参数
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bonusParam:remove')")
    @Log(title = "提成规则参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizBonusRuleParamService.deleteBaoliBizBonusRuleParamByIds(ids));
    }
}
