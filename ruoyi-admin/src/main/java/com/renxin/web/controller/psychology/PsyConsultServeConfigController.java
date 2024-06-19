package com.renxin.web.controller.psychology;

import com.renxin.common.annotation.Log;
import com.renxin.common.core.controller.BaseController;
import com.renxin.common.core.domain.AjaxResult;
import com.renxin.common.core.page.TableDataInfo;
import com.renxin.common.enums.BusinessType;
import com.renxin.common.utils.poi.ExcelUtil;
import com.renxin.psychology.domain.PsyConsultServe;
import com.renxin.psychology.domain.PsyConsultServeConfig;
import com.renxin.psychology.request.PsyConsultServeConfigReq;
import com.renxin.psychology.service.IPsyConsultServeConfigService;
import com.renxin.psychology.service.IPsyConsultServeService;
import com.renxin.psychology.vo.PsyConsultServeConfigVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 咨询服务配置Controller
 * 
 * @author renxin
 * @date 2023-07-14
 */
@RestController
@RequestMapping("/psychology/serveConfig")
public class PsyConsultServeConfigController extends BaseController
{

    @Resource
    private IPsyConsultServeService psyConsultServeService;

    @Resource
    private IPsyConsultServeConfigService psyConsultServeConfigService;

    /**
     * 查询咨询服务配置列表
     */
//    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultServeConfigReq req)
    {
        startPage();
        List<PsyConsultServeConfig> list = psyConsultServeConfigService.getList(req);
        return getDataTable(list);
    }

    /**
     * 查询咨询服务配置列表
     */
    @GetMapping("/getList")
    public AjaxResult getList(PsyConsultServeConfigReq req)
    {
        req.setStatus("0");
        return AjaxResult.success(psyConsultServeConfigService.getList(req));
    }

    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:list')")
    @GetMapping("/getServeRef")
    public AjaxResult getServeRef(PsyConsultServe req) {
        return AjaxResult.success(psyConsultServeService.getServeRef(req));
    }

    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:list')")
    @GetMapping("/getConsultServeRef")
    public AjaxResult getConsultServeRef(PsyConsultServe req) {
        return AjaxResult.success(psyConsultServeService.getConsultServeRef(req));
    }

    /**
     * 导出咨询服务配置列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:export')")
    @Log(title = "咨询服务配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultServeConfigReq req)
    {
        List<PsyConsultServeConfig> list = psyConsultServeConfigService.getList(req);
        ExcelUtil<PsyConsultServeConfig> util = new ExcelUtil<PsyConsultServeConfig>(PsyConsultServeConfig.class);
        util.exportExcel(response, list, "咨询服务配置数据");
    }

    /**
     * 获取咨询服务配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultServeConfigService.getOne(id));
    }

    /**
     * 新增咨询服务配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:add')")
    @Log(title = "咨询服务配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultServeConfigVO req)
    {
        return toAjax(psyConsultServeConfigService.add(req));
    }

    /**
     * 修改咨询服务配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:edit')")
    @Log(title = "咨询服务配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultServeConfigVO req)
    {
        return toAjax(psyConsultServeConfigService.update(req));
    }

    /**
     * 删除咨询服务配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:serveConfig:remove')")
    @Log(title = "咨询服务配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultServeConfigService.deleteAll(ids));
    }
}
