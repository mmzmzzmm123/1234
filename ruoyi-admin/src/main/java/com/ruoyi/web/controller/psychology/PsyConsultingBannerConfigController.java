package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.service.IPsyConsultingBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询banner配置Controller
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/psychology/psyBannerConfig")
public class PsyConsultingBannerConfigController extends BaseController
{
    @Autowired
    private IPsyConsultingBannerConfigService psyConsultingBannerConfigService;

    /**
     * 查询咨询banner配置列表 psychology:consultant:list
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultingBannerConfigVO data)
    {
        startPage();
        List<PsyConsultingBannerConfig> list = psyConsultingBannerConfigService.getList(data);
        return getDataTable(list);
    }

    /**
     * 导出咨询banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:export')")
    @Log(title = "咨询banner配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultingBannerConfigVO req)
    {
        List<PsyConsultingBannerConfig> list = psyConsultingBannerConfigService.getList(req);
        ExcelUtil<PsyConsultingBannerConfig> util = new ExcelUtil<PsyConsultingBannerConfig>(PsyConsultingBannerConfig.class);
        util.exportExcel(response, list, "咨询banner配置数据");
    }

    /**
     * 获取咨询banner配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultingBannerConfigService.getOne(id));
    }

    /**
     * 新增咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:add')")
    @Log(title = "咨询banner配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultingBannerConfigVO req)
    {
        return toAjax(psyConsultingBannerConfigService.add(req));
    }

    /**
     * 修改咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:edit')")
    @Log(title = "咨询banner配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultingBannerConfigVO req)
    {
        return toAjax(psyConsultingBannerConfigService.update(req));
    }

    /**
     * 删除咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:remove')")
    @Log(title = "咨询banner配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/deleteAll")
    public AjaxResult deleteAll(@RequestBody List<Long> ids)
    {
        return toAjax(psyConsultingBannerConfigService.deleteAll(ids));
    }

    @PreAuthorize("@ss.hasPermi('psychology:banner:remove')")
    @Log(title = "咨询banner配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestBody Long id)
    {
        return toAjax(psyConsultingBannerConfigService.delete(id));
    }
}
