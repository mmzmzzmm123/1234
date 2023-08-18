package com.ruoyi.web.controller.psychology;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.psychology.domain.PsyConsult;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.request.PsyAdminConsultReq;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.service.IPsyConsultConfigService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.vo.PsyConsultVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 心理咨询师Controller
 *
 * @author ruoyi
 * @date 2023-06-25
 */
@RestController
@RequestMapping("/psychology/consult")
public class PsyConsultController extends BaseController
{
    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyConsultConfigService psyConsultConfigService;

    @GetMapping("/getAttrs/{dictTypes}")
    public AjaxResult getAttrs(@PathVariable(value = "dictTypes") String[] dictTypes)
    {
        return AjaxResult.success(psyConsultConfigService.getConfigByTypes(dictTypes));
    }

    /**
     * 查询心理咨询师列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultVO psyConsult)
    {
        startPage();
        List<PsyConsult> list = psyConsultService.getList(psyConsult);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('psychology:consult:list')")
    @GetMapping("/adminList")
    public TableDataInfo list(PsyAdminConsultReq psyConsult)
    {
        startPage();
        List<PsyConsult> list = psyConsultService.getList(psyConsult);
        return getDataTable(list);
    }

    /**
     * 查询所有心理咨询师
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:list')")
    @GetMapping("/getAll")
    public AjaxResult getAll(PsyConsultVO psyConsult)
    {
        return AjaxResult.success(psyConsultService.getList(psyConsult));
    }

    /**
     * 导出心理咨询师列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:export')")
    @Log(title = "心理咨询师", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyAdminConsultReq psyConsult)
    {
        List<PsyConsult> list = psyConsultService.getList(psyConsult);
        ExcelUtil<PsyConsult> util = new ExcelUtil<PsyConsult>(PsyConsult.class);
        util.exportExcel(response, list, "心理咨询师数据");
    }

    /**
     * 获取心理咨询师详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultService.getOne(id));
    }

    /**
     * 新增咨询服务配置
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:edit')")
    @Log(title = "咨询服务配置", businessType = BusinessType.INSERT)
    @PostMapping("/refConsultServe")
    public AjaxResult refConsultServe(@RequestBody PsyRefConsultServeReq req)
    {
        return psyConsultService.refConsultServe(req);
    }

    @PreAuthorize("@ss.hasPermi('psychology:consult:edit')")
    @PostMapping("/delConsultServeRef")
    public AjaxResult delConsultServeRef(@RequestBody PsyConsultServe req)
    {
        if (req.getServeId() == null || req.getConsultId() == null) {
            return AjaxResult.error();
        }
        return toAjax(psyConsultService.delConsultServeRef(req));
    }

    /**
     * 新增心理咨询师
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:add')")
    @Log(title = "心理咨询师", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultVO psyConsult)
    {
        if (!SecurityUtils.isAdmin(getUserId())) {
            return error("必须是超级管理员才可以添加咨询师");
        }
        return psyConsultService.add(psyConsult);
    }

    /**
     * 修改心理咨询师
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:edit')")
    @Log(title = "心理咨询师", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultVO psyConsult)
    {
        return psyConsultService.update(psyConsult);
    }

    /**
     * 删除心理咨询师
     */
    @PreAuthorize("@ss.hasPermi('psychology:consult:remove')")
    @Log(title = "心理咨询师", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        if (!SecurityUtils.isAdmin(getUserId())) {
            return error("必须是超级管理员才可以删除咨询师");
        }
        if (ids == null || ids.length == 0) {
            return error("请选择数据进行删除");
        }
        return toAjax(psyConsultService.deleteAll(ids));
    }
}
