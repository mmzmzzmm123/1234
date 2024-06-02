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
import com.baoli.store.domain.BaoliBizFeeRateSchema;
import com.baoli.store.service.IBaoliBizFeeRateSchemaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商户费率计划Controller
 * 
 * @author niujs
 * @date 2024-04-15
 */
@RestController
@RequestMapping("/feeManage/feeRate")
public class BaoliBizFeeRateSchemaController extends BaseController
{
    @Autowired
    private IBaoliBizFeeRateSchemaService baoliBizFeeRateSchemaService;

    /**
     * 查询商户费率计划列表
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        startPage();
        List<BaoliBizFeeRateSchema> list = baoliBizFeeRateSchemaService.selectBaoliBizFeeRateSchemaList(baoliBizFeeRateSchema);
        return getDataTable(list);
    }

    /**
     * 导出商户费率计划列表
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:export')")
    @Log(title = "商户费率计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        List<BaoliBizFeeRateSchema> list = baoliBizFeeRateSchemaService.selectBaoliBizFeeRateSchemaList(baoliBizFeeRateSchema);
        ExcelUtil<BaoliBizFeeRateSchema> util = new ExcelUtil<BaoliBizFeeRateSchema>(BaoliBizFeeRateSchema.class);
        util.exportExcel(response, list, "商户费率计划数据");
    }

    /**
     * 获取商户费率计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizFeeRateSchemaService.selectBaoliBizFeeRateSchemaById(id));
    }

    /**
     * 新增商户费率计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:add')")
    @Log(title = "商户费率计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        return toAjax(baoliBizFeeRateSchemaService.insertBaoliBizFeeRateSchema(baoliBizFeeRateSchema));
    }

    /**
     * 新增商户费率计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:add')")
    @Log(title = "商户费率计划", businessType = BusinessType.INSERT)
    @PostMapping("/batchInsert")
    public AjaxResult add(@RequestBody List<BaoliBizFeeRateSchema> baoliBizFeeRateSchema)
    {
        return toAjax(baoliBizFeeRateSchemaService.insertBaoliBizFeeRateSchemaBatch(baoliBizFeeRateSchema));
    }

    /**
     * 修改商户费率计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:edit')")
    @Log(title = "商户费率计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizFeeRateSchema baoliBizFeeRateSchema)
    {
        return toAjax(baoliBizFeeRateSchemaService.updateBaoliBizFeeRateSchema(baoliBizFeeRateSchema));
    }

    /**
     * 删除商户费率计划
     */
    @PreAuthorize("@ss.hasPermi('feeManage:feeRate:remove')")
    @Log(title = "商户费率计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizFeeRateSchemaService.deleteBaoliBizFeeRateSchemaByIds(ids));
    }
}
