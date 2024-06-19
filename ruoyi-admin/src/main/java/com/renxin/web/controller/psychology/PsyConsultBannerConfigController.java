package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.psychology.domain.PsyConsultBannerConfig;
import com.renxin.psychology.service.IPsyConsultBannerConfigService;
import com.renxin.psychology.vo.PsyConsultBannerConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询banner配置Controller
 * 
 * @author renxin
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
