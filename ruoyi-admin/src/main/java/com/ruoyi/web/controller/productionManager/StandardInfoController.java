package com.ruoyi.web.controller.productionManager;

import java.util.List;
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
import com.ruoyi.productionManager.domain.StandardInfo;
import com.ruoyi.productionManager.service.IStandardInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标准信息Controller
 * 
 * @author ruoyi
 * @date 2021-10-13
 */
@RestController
@RequestMapping("/productionManager/standard")
public class StandardInfoController extends BaseController
{
    @Autowired
    private IStandardInfoService standardInfoService;

    /**
     * 查询标准信息列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:list')")
    @GetMapping("/list")
    public TableDataInfo list(StandardInfo standardInfo)
    {
        startPage();
        List<StandardInfo> list = standardInfoService.selectStandardInfoList(standardInfo);
        return getDataTable(list);
    }

    /**
     * 导出标准信息列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:export')")
    @Log(title = "标准信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(StandardInfo standardInfo)
    {
        List<StandardInfo> list = standardInfoService.selectStandardInfoList(standardInfo);
        ExcelUtil<StandardInfo> util = new ExcelUtil<StandardInfo>(StandardInfo.class);
        return util.exportExcel(list, "标准信息数据");
    }

    /**
     * 获取标准信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:query')")
    @GetMapping(value = "/{standardId}")
    public AjaxResult getInfo(@PathVariable("standardId") Long standardId)
    {
        return AjaxResult.success(standardInfoService.selectStandardInfoByStandardId(standardId));
    }

    /**
     * 新增标准信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:add')")
    @Log(title = "标准信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StandardInfo standardInfo)
    {
        return toAjax(standardInfoService.insertStandardInfo(standardInfo));
    }

    /**
     * 修改标准信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:edit')")
    @Log(title = "标准信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StandardInfo standardInfo)
    {
        return toAjax(standardInfoService.updateStandardInfo(standardInfo));
    }

    /**
     * 删除标准信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:standard:remove')")
    @Log(title = "标准信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{standardIds}")
    public AjaxResult remove(@PathVariable Long[] standardIds)
    {
        return toAjax(standardInfoService.deleteStandardInfoByStandardIds(standardIds));
    }
}
