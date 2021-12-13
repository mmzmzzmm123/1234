package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.PanoramaInfo;
import com.ruoyi.system.service.IPanoramaInfoService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 全景图管理Controller
 * 
 * @author ruoyi
 * @date 2021-11-01
 */
@RestController
@RequestMapping("/system/info")
public class PanoramaInfoController extends BaseController
{
    @Autowired
    private IPanoramaInfoService panoramaInfoService;

    /**
     * 查询全景图管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(PanoramaInfo panoramaInfo)
    {
        startPage();
        List<PanoramaInfo> list = panoramaInfoService.selectPanoramaInfoList(panoramaInfo);
        return getDataTable(list);
    }

    /**
     * 导出全景图管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "全景图管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PanoramaInfo panoramaInfo)
    {
        List<PanoramaInfo> list = panoramaInfoService.selectPanoramaInfoList(panoramaInfo);
        ExcelUtil<PanoramaInfo> util = new ExcelUtil<PanoramaInfo>(PanoramaInfo.class);
        return util.exportExcel(list, "全景图管理数据");
    }

    /**
     * 获取全景图管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(panoramaInfoService.selectPanoramaInfoById(id));
    }

    /**
     * 新增全景图管理
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "全景图管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PanoramaInfo panoramaInfo)
    {
        return toAjax(panoramaInfoService.insertPanoramaInfo(panoramaInfo));
    }

    /**
     * 修改全景图管理
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "全景图管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PanoramaInfo panoramaInfo)
    {
        return toAjax(panoramaInfoService.updatePanoramaInfo(panoramaInfo));
    }

    /**
     * 删除全景图管理
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "全景图管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(panoramaInfoService.deletePanoramaInfoByIds(ids));
    }
}
