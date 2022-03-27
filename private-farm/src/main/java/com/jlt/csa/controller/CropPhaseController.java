package com.jlt.csa.controller;

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
import com.jlt.csa.domain.CropPhase;
import com.jlt.csa.service.ICropPhaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作物生长阶段Controller
 * 
 * @author JiaLeitao
 * @date 2022-03-26
 */
@RestController
@RequestMapping("/csa/phase")
public class CropPhaseController extends BaseController
{
    @Autowired
    private ICropPhaseService cropPhaseService;

    /**
     * 查询作物生长阶段列表
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:list')")
    @GetMapping("/list")
    public TableDataInfo list(CropPhase cropPhase)
    {
        startPage();
        List<CropPhase> list = cropPhaseService.selectCropPhaseList(cropPhase);
        return getDataTable(list);
    }

    /**
     * 导出作物生长阶段列表
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:export')")
    @Log(title = "作物生长阶段", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CropPhase cropPhase)
    {
        List<CropPhase> list = cropPhaseService.selectCropPhaseList(cropPhase);
        ExcelUtil<CropPhase> util = new ExcelUtil<CropPhase>(CropPhase.class);
        util.exportExcel(response, list, "作物生长阶段数据");
    }

    /**
     * 获取作物生长阶段详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cropPhaseService.selectCropPhaseById(id));
    }

    /**
     * 新增作物生长阶段
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:add')")
    @Log(title = "作物生长阶段", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CropPhase cropPhase)
    {
        return toAjax(cropPhaseService.insertCropPhase(cropPhase));
    }

    /**
     * 修改作物生长阶段
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:edit')")
    @Log(title = "作物生长阶段", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CropPhase cropPhase)
    {
        return toAjax(cropPhaseService.updateCropPhase(cropPhase));
    }

    /**
     * 删除作物生长阶段
     */
    @PreAuthorize("@ss.hasPermi('csa:phase:remove')")
    @Log(title = "作物生长阶段", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cropPhaseService.deleteCropPhaseByIds(ids));
    }
}
