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
import com.ruoyi.productionManager.domain.StandardInfoDetails;
import com.ruoyi.productionManager.service.IStandardInfoDetailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 标准信息文件Controller
 * 
 * @author ruoyi
 * @date 2021-10-18
 */
@RestController
@RequestMapping("/productionManager/details")
public class StandardInfoDetailsController extends BaseController
{
    @Autowired
    private IStandardInfoDetailsService standardInfoDetailsService;

    /**
     * 查询标准信息文件列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(StandardInfoDetails standardInfoDetails)
    {
        startPage();
        List<StandardInfoDetails> list = standardInfoDetailsService.selectStandardInfoDetailsList(standardInfoDetails);
        return getDataTable(list);
    }

    /**
     * 导出标准信息文件列表
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:export')")
    @Log(title = "标准信息文件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(StandardInfoDetails standardInfoDetails)
    {
        List<StandardInfoDetails> list = standardInfoDetailsService.selectStandardInfoDetailsList(standardInfoDetails);
        ExcelUtil<StandardInfoDetails> util = new ExcelUtil<StandardInfoDetails>(StandardInfoDetails.class);
        return util.exportExcel(list, "标准信息文件数据");
    }

    /**
     * 获取标准信息文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:query')")
    @GetMapping(value = "/{detailsId}")
    public AjaxResult getInfo(@PathVariable("detailsId") Long detailsId)
    {
        return AjaxResult.success(standardInfoDetailsService.selectStandardInfoDetailsByDetailsId(detailsId));
    }

    /**
     * 新增标准信息文件
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:add')")
    @Log(title = "标准信息文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StandardInfoDetails standardInfoDetails)
    {
        return toAjax(standardInfoDetailsService.insertStandardInfoDetails(standardInfoDetails));
    }

    /**
     * 修改标准信息文件
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:edit')")
    @Log(title = "标准信息文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StandardInfoDetails standardInfoDetails)
    {
        return toAjax(standardInfoDetailsService.updateStandardInfoDetails(standardInfoDetails));
    }

    /**
     * 删除标准信息文件
     */
    @PreAuthorize("@ss.hasPermi('productionManager:details:remove')")
    @Log(title = "标准信息文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailsIds}")
    public AjaxResult remove(@PathVariable Long[] detailsIds)
    {
        return toAjax(standardInfoDetailsService.deleteStandardInfoDetailsByDetailsIds(detailsIds));
    }
}
