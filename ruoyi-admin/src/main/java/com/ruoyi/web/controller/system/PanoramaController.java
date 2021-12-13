package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.Panorama;
import com.ruoyi.system.service.IPanoramaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 全景管理Controller
 * 
 * @author ruoyi
 * @date 2021-11-02
 */
@RestController
@RequestMapping("/system/panorama")
public class PanoramaController extends BaseController
{
    @Autowired
    private IPanoramaService panoramaService;

    /**
     * 查询全景管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:list')")
    @GetMapping("/list")
    public TableDataInfo list(Panorama panorama)
    {
        startPage();
        List<Panorama> list = panoramaService.selectPanoramaList(panorama);
        return getDataTable(list);
    }

    /**
     * 导出全景管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:export')")
    @Log(title = "全景管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Panorama panorama)
    {
        List<Panorama> list = panoramaService.selectPanoramaList(panorama);
        ExcelUtil<Panorama> util = new ExcelUtil<Panorama>(Panorama.class);
        return util.exportExcel(list, "全景管理数据");
    }

    /**
     * 获取全景管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(panoramaService.selectPanoramaById(id));
    }

    /**
     * 新增全景管理
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:add')")
    @Log(title = "全景管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Panorama panorama)
    {
        return toAjax(panoramaService.insertPanorama(panorama));
    }

    /**
     * 修改全景管理
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:edit')")
    @Log(title = "全景管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Panorama panorama)
    {
        return toAjax(panoramaService.updatePanorama(panorama));
    }

    /**
     * 删除全景管理
     */
    @PreAuthorize("@ss.hasPermi('system:panorama:remove')")
    @Log(title = "全景管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(panoramaService.deletePanoramaByIds(ids));
    }
}
