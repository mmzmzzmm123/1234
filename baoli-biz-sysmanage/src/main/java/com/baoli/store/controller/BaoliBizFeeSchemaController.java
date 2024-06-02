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
import com.baoli.store.domain.BaoliBizFeeSchema;
import com.baoli.store.service.IBaoliBizFeeSchemaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户费用计划Controller
 * 
 * @author niujs
 * @date 2024-04-15
 */
@RestController
@RequestMapping("/feeManage/fee")
public class BaoliBizFeeSchemaController extends BaseController
{
    @Autowired
    private IBaoliBizFeeSchemaService baoliBizFeeSchemaService;

    /**
     * 查询商户费用计划列表
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizFeeSchema baoliBizFeeSchema)
    {
        startPage();
        List<BaoliBizFeeSchema> list = baoliBizFeeSchemaService.selectBaoliBizFeeSchemaList(baoliBizFeeSchema);
        return getDataTable(list);
    }

    /**
     * 导出商户费用计划列表
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:export')")
    @Log(title = "商户费用计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizFeeSchema baoliBizFeeSchema)
    {
        List<BaoliBizFeeSchema> list = baoliBizFeeSchemaService.selectBaoliBizFeeSchemaList(baoliBizFeeSchema);
        ExcelUtil<BaoliBizFeeSchema> util = new ExcelUtil<BaoliBizFeeSchema>(BaoliBizFeeSchema.class);
        util.exportExcel(response, list, "商户费用计划数据");
    }

    /**
     * 获取商户费用计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizFeeSchemaService.selectBaoliBizFeeSchemaById(id));
    }

    /**
     * 新增商户费用计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:add')")
    @Log(title = "商户费用计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizFeeSchema baoliBizFeeSchema)
    {
        return toAjax(baoliBizFeeSchemaService.insertBaoliBizFeeSchema(baoliBizFeeSchema));
    }

    /**
     * 新增商户费用计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:add')")
    @Log(title = "商户费用计划", businessType = BusinessType.INSERT)
    @PostMapping("/batchInsert")
    public AjaxResult add(@RequestBody List<BaoliBizFeeSchema> baoliBizFeeSchema)
    {
        return toAjax(baoliBizFeeSchemaService.insertBaoliBizFeeSchemaBatch(baoliBizFeeSchema));
    }
    /**
     * 修改商户费用计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:edit')")
    @Log(title = "商户费用计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizFeeSchema baoliBizFeeSchema)
    {
        return toAjax(baoliBizFeeSchemaService.updateBaoliBizFeeSchema(baoliBizFeeSchema));
    }

    /**
     * 删除商户费用计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:fee:remove')")
    @Log(title = "商户费用计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizFeeSchemaService.deleteBaoliBizFeeSchemaByIds(ids));
    }
}
