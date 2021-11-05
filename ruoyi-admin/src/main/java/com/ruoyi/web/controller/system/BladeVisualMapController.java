package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.common.core.page.VisualRespEmbData;
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
import com.ruoyi.system.domain.BladeVisualMap;
import com.ruoyi.system.service.IBladeVisualMapService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 可视化地图配置Controller
 *
 * @author yangsj
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/avue/api/map")
public class BladeVisualMapController extends BaseController
{
    @Autowired
    private IBladeVisualMapService bladeVisualMapService;

    /**
     * 查询可视化地图配置列表
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_map:list')")
    @GetMapping("/list")
    public VisualRespEmbData list(BladeVisualMap bladeVisualMap)
    {
        startPage();
        List<BladeVisualMap> list = bladeVisualMapService.selectBladeVisualMapList(bladeVisualMap);
        return getVisualEmbData(list);
//        return getDataTable(list);
    }

//    /**
//     * 导出可视化地图配置列表
//     */
////    @PreAuthorize("@ss.hasPermi('system:visual_map:export')")
//    @Log(title = "可视化地图配置", businessType = BusinessType.EXPORT)
//    @GetMapping("/export")
//    public AjaxResult export(BladeVisualMap bladeVisualMap)
//    {
//        List<BladeVisualMap> list = bladeVisualMapService.selectBladeVisualMapList(bladeVisualMap);
//        ExcelUtil<BladeVisualMap> util = new ExcelUtil<BladeVisualMap>(BladeVisualMap.class);
//        return util.exportExcel(list, "可视化地图配置数据");
//    }

    /**
     * 获取可视化地图配置详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_map:query')")
    @GetMapping(value = "/detail")
    public AjaxResult getInfo(Long id)
    {
        return AjaxResult.success(bladeVisualMapService.selectBladeVisualMapById(id));
    }

    /**
     * 新增可视化地图配置
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_map:add')")
    @Log(title = "可视化地图配置", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody BladeVisualMap bladeVisualMap)
    {
        return toAjax(bladeVisualMapService.insertBladeVisualMap(bladeVisualMap));
    }

    /**
     * 修改可视化地图配置
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_map:edit')")
    @Log(title = "可视化地图配置", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody BladeVisualMap bladeVisualMap)
    {
        return toAjax(bladeVisualMapService.updateBladeVisualMap(bladeVisualMap));
    }

    /**
     * 删除可视化地图配置
     */
//    @PreAuthorize("@ss.hasPermi('system:visual_map:remove')")
    @Log(title = "可视化地图配置", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
    public AjaxResult remove(Long[] ids)
    {
        return toAjax(bladeVisualMapService.deleteBladeVisualMapByIds(ids));
    }
}
