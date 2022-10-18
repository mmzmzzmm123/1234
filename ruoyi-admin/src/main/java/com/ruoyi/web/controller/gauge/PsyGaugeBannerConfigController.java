package com.ruoyi.web.controller.gauge;

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
import com.ruoyi.gauge.domain.PsyGaugeBannerConfig;
import com.ruoyi.gauge.service.IPsyGaugeBannerConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测评banner配置Controller
 * 
 * @author ruoyi
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/banner/config")
public class PsyGaugeBannerConfigController extends BaseController
{
    @Autowired
    private IPsyGaugeBannerConfigService psyGaugeBannerConfigService;

    /**
     * 查询测评banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('banner:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyGaugeBannerConfig psyGaugeBannerConfig)
    {
        startPage();
        List<PsyGaugeBannerConfig> list = psyGaugeBannerConfigService.selectPsyGaugeBannerConfigList(psyGaugeBannerConfig);
        return getDataTable(list);
    }

    /**
     * 导出测评banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('banner:config:export')")
    @Log(title = "测评banner配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyGaugeBannerConfig psyGaugeBannerConfig)
    {
        List<PsyGaugeBannerConfig> list = psyGaugeBannerConfigService.selectPsyGaugeBannerConfigList(psyGaugeBannerConfig);
        ExcelUtil<PsyGaugeBannerConfig> util = new ExcelUtil<PsyGaugeBannerConfig>(PsyGaugeBannerConfig.class);
        util.exportExcel(response, list, "测评banner配置数据");
    }

    /**
     * 获取测评banner配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('banner:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyGaugeBannerConfigService.selectPsyGaugeBannerConfigById(id));
    }

    /**
     * 新增测评banner配置
     */
    @PreAuthorize("@ss.hasPermi('banner:config:add')")
    @Log(title = "测评banner配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyGaugeBannerConfig psyGaugeBannerConfig)
    {
        psyGaugeBannerConfig.setCreateBy(getUsername());
        return toAjax(psyGaugeBannerConfigService.insertPsyGaugeBannerConfig(psyGaugeBannerConfig));
    }

    /**
     * 修改测评banner配置
     */
    @PreAuthorize("@ss.hasPermi('banner:config:edit')")
    @Log(title = "测评banner配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyGaugeBannerConfig psyGaugeBannerConfig)
    {
        return toAjax(psyGaugeBannerConfigService.updatePsyGaugeBannerConfig(psyGaugeBannerConfig));
    }

    /**
     * 删除测评banner配置
     */
    @PreAuthorize("@ss.hasPermi('banner:config:remove')")
    @Log(title = "测评banner配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(psyGaugeBannerConfigService.deletePsyGaugeBannerConfigByIds(ids));
    }
}
