package com.ruoyi.web.controller.psychology;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.psychology.request.PsyAdminPartnerReq;
import com.ruoyi.psychology.service.IPsyConsultService;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.service.IPsyConsultPartnerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 咨询师入驻申请Controller
 * 
 * @author ruoyi
 * @date 2023-11-07
 */
@RestController
@RequestMapping("/psychology/partner")
public class PsyConsultPartnerController extends BaseController
{

    @Resource
    private IPsyConsultPartnerService psyConsultPartnerService;

    /**
     * 查询咨询师入驻申请列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:partner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyAdminPartnerReq req)
    {
        startPage();
        List<PsyConsultPartner> list = psyConsultPartnerService.getList(req);
        return getDataTable(list);
    }

    /**
     * 导出咨询师入驻申请列表
     */
    @PreAuthorize("@ss.hasPermi('psychology:partner:export')")
    @Log(title = "咨询师入驻申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyAdminPartnerReq psyConsultPartner)
    {
        List<PsyConsultPartner> list = psyConsultPartnerService.getList(psyConsultPartner);
        ExcelUtil<PsyConsultPartner> util = new ExcelUtil<PsyConsultPartner>(PsyConsultPartner.class);
        util.exportExcel(response, list, "咨询师入驻申请数据");
    }

    /**
     * 获取咨询师入驻申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('psychology:partner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultPartnerService.getInfoById(id));
    }

    /**
     * 修改咨询师入驻申请
     */
    @PreAuthorize("@ss.hasPermi('psychology:partner:edit')")
    @Log(title = "咨询师入驻申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultPartner psyConsultPartner)
    {
        return toAjax(psyConsultPartnerService.save(psyConsultPartner));
    }

    /**
     * 修改咨询师入驻申请
     */
    @PreAuthorize("@ss.hasPermi('psychology:partner:account')")
    @Log(title = "咨询师账户开通", businessType = BusinessType.UPDATE)
    @PostMapping("/createUser/{id}")
    public AjaxResult createUser(@PathVariable("id") Long id)
    {
        return psyConsultPartnerService.createUser(id);
    }

}
