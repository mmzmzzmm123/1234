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
import com.ruoyi.system.domain.BladeVisualConfig;
import com.ruoyi.system.service.IBladeVisualConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 可视化配置Controller
 *
 * @author yangsj
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/avue/config")
public class BladeVisualConfigController extends BaseController
{
    @Autowired
    private IBladeVisualConfigService bladeVisualConfigService;

    /**
     * 查询可视化配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:list')")
    @GetMapping("/list")
    public TableDataInfo list(BladeVisualConfig bladeVisualConfig)
    {
        startPage();
        List<BladeVisualConfig> list = bladeVisualConfigService.selectBladeVisualConfigList(bladeVisualConfig);
        return getDataTable(list);
    }

    /**
     * 导出可视化配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:export')")
    @Log(title = "可视化配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BladeVisualConfig bladeVisualConfig)
    {
        List<BladeVisualConfig> list = bladeVisualConfigService.selectBladeVisualConfigList(bladeVisualConfig);
        ExcelUtil<BladeVisualConfig> util = new ExcelUtil<BladeVisualConfig>(BladeVisualConfig.class);
        return util.exportExcel(list, "可视化配置数据");
    }

    /**
     * 获取可视化配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bladeVisualConfigService.selectBladeVisualConfigById(id));
    }

    /**
     * 新增可视化配置
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:add')")
    @Log(title = "可视化配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BladeVisualConfig bladeVisualConfig)
    {
        return toAjax(bladeVisualConfigService.insertBladeVisualConfig(bladeVisualConfig));
    }

    /**
     * 修改可视化配置
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:edit')")
    @Log(title = "可视化配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BladeVisualConfig bladeVisualConfig)
    {
        return toAjax(bladeVisualConfigService.updateBladeVisualConfig(bladeVisualConfig));
    }

    /**
     * 删除可视化配置
     */
    @PreAuthorize("@ss.hasPermi('system:visual_config:remove')")
    @Log(title = "可视化配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bladeVisualConfigService.deleteBladeVisualConfigByIds(ids));
    }
}
