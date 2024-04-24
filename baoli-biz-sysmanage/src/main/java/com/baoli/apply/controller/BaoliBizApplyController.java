package com.baoli.apply.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

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
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.baoli.apply.domain.BaoliBizApply;
import com.baoli.apply.service.IBaoliBizApplyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import sun.plugin2.util.SystemUtil;

/**
 * 申请管理Controller
 * 
 * @author niujs
 * @date 2024-04-21
 */
@RestController
@RequestMapping("/apply/apply")
public class BaoliBizApplyController extends BaseController
{
    @Autowired
    private IBaoliBizApplyService baoliBizApplyService;

    /**
     * 查询申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizApply baoliBizApply)
    {
        startPage();
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        return getDataTable(list);
    }

    /**
     * 导出申请管理列表
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:export')")
    @Log(title = "申请管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizApply baoliBizApply)
    {
        List<BaoliBizApply> list = baoliBizApplyService.selectBaoliBizApplyList(baoliBizApply);
        ExcelUtil<BaoliBizApply> util = new ExcelUtil<BaoliBizApply>(BaoliBizApply.class);
        util.exportExcel(response, list, "申请管理数据");
    }

    /**
     * 获取申请管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizApplyService.selectBaoliBizApplyById(id));
    }

    /**
     * 新增申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:add')")
    @Log(title = "申请管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizApply baoliBizApply)
    {
        baoliBizApply.setApplicantId(getUserId());
        if(baoliBizApply.getStatus() == null){
            baoliBizApply.setStatus("02");
        }
        return toAjax(baoliBizApplyService.insertBaoliBizApply(baoliBizApply));
    }

    /**
     * 修改申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:edit')")
    @Log(title = "申请管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizApply baoliBizApply)
    {
        return toAjax(baoliBizApplyService.updateBaoliBizApply(baoliBizApply));
    }

    /**
     * 删除申请管理
     */
    @PreAuthorize("@ss.hasPermi('apply:apply:remove')")
    @Log(title = "申请管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizApplyService.deleteBaoliBizApplyByIds(ids));
    }
}
