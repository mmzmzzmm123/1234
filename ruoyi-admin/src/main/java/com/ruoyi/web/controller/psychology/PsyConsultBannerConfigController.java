package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsultBannerConfig;
import com.ruoyi.psychology.service.IPsyConsultBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultBannerConfigVO;
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
public class PsyConsultBannerConfigController extends BaseController
{
    @Autowired
    private IPsyConsultBannerConfigService psyConsultBannerConfigService;

    /**
     * 查询咨询banner配置列表 psychology:consultant:list
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultBannerConfigVO data)
    {
        startPage();
        List<PsyConsultBannerConfig> list = psyConsultBannerConfigService.getList(data);
        return getDataTable(list);
    }

    /**
     * 导出咨询banner配置列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:export')")
    @Log(title = "咨询banner配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultBannerConfigVO req)
    {
        List<PsyConsultBannerConfig> list = psyConsultBannerConfigService.getList(req);
        ExcelUtil<PsyConsultBannerConfig> util = new ExcelUtil<PsyConsultBannerConfig>(PsyConsultBannerConfig.class);
        util.exportExcel(response, list, "咨询banner配置数据");
    }

    /**
     * 获取咨询banner配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultBannerConfigService.getOne(id));
    }

    /**
     * 新增咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:add')")
    @Log(title = "咨询banner配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultBannerConfigVO req)
    {
        return toAjax(psyConsultBannerConfigService.add(req));
    }

    /**
     * 修改咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:edit')")
    @Log(title = "咨询banner配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultBannerConfigVO req)
    {
        return toAjax(psyConsultBannerConfigService.update(req));
    }

    /**
     * 删除咨询banner配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:banner:remove')")
    @Log(title = "咨询banner配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultBannerConfigService.deleteAll(ids));
    }
}
