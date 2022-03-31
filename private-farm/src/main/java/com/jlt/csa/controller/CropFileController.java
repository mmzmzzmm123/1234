package com.jlt.csa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DateUtils;
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
import com.jlt.csa.domain.CropFile;
import com.jlt.csa.service.ICropFileService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 作物档案Controller
 * 
 * @author 郏磊涛
 * @date 2022-03-31
 */
@RestController
@RequestMapping("/csa/cropfile")
public class CropFileController extends BaseController
{
    @Autowired
    private ICropFileService cropFileService;

    /**
     * 查询作物档案列表
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:list')")
    @GetMapping("/list")
    public TableDataInfo list(CropFile cropFile)
    {
        startPage();
        List<CropFile> list = cropFileService.selectCropFileList(cropFile);
        return getDataTable(list);
    }

    /**
     * 导出作物档案列表
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:export')")
    @Log(title = "作物档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CropFile cropFile)
    {
        List<CropFile> list = cropFileService.selectCropFileList(cropFile);
        ExcelUtil<CropFile> util = new ExcelUtil<CropFile>(CropFile.class);
        util.exportExcel(response, list, "作物档案数据");
    }

    /**
     * 获取作物档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:query')")
    @GetMapping(value = "/{cropId}")
    public AjaxResult getInfo(@PathVariable("cropId") Long cropId)
    {
        return AjaxResult.success(cropFileService.selectCropFileByCropId(cropId));
    }

    /**
     * 新增作物档案
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:add')")
    @Log(title = "作物档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CropFile cropFile)
    {
        cropFile.setCreateBy(getUsername());
        cropFile.setCreateTime(DateUtils.getNowDate());
        return toAjax(cropFileService.insertCropFile(cropFile));
    }

    /**
     * 修改作物档案
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:edit')")
    @Log(title = "作物档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CropFile cropFile)
    {
        cropFile.setUpdateBy(getUsername());
        cropFile.setUpdateTime(DateUtils.getNowDate());
        return toAjax(cropFileService.updateCropFile(cropFile));
    }

    /**
     * 删除作物档案
     */
    @PreAuthorize("@ss.hasPermi('csa:cropfile:remove')")
    @Log(title = "作物档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{cropIds}")
    public AjaxResult remove(@PathVariable Long[] cropIds)
    {
        return toAjax(cropFileService.deleteCropFileByCropIds(cropIds));
    }
}
