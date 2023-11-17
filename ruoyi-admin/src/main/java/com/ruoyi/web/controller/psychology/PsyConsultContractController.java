package com.ruoyi.web.controller.psychology;

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
import com.ruoyi.psychology.domain.PsyConsultContract;
import com.ruoyi.psychology.service.IPsyConsultContractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 咨询师合同协议Controller
 * 
 * @author ruoyi
 * @date 2023-11-16
 */
@RestController
@RequestMapping("/psychology/contract")
public class PsyConsultContractController extends BaseController
{
    @Autowired
    private IPsyConsultContractService psyConsultContractService;

    /**
     * 查询咨询师合同协议列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(PsyConsultContract req)
    {
        startPage();
        List<PsyConsultContract> list = psyConsultContractService.getList(req);
        return getDataTable(list);
    }

    /**
     * 查询咨询师合同协议列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:list')")
    @GetMapping("/exist")
    public AjaxResult exist(PsyConsultContract req)
    {
        return AjaxResult.success(psyConsultContractService.countExistContract(req));
    }

    /**
     * 导出咨询师合同协议列表
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:export')")
    @Log(title = "咨询师合同协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PsyConsultContract req)
    {
        List<PsyConsultContract> list = psyConsultContractService.getList(req);
        ExcelUtil<PsyConsultContract> util = new ExcelUtil<PsyConsultContract>(PsyConsultContract.class);
        util.exportExcel(response, list, "咨询师合同协议数据");
    }

    /**
     * 获取咨询师合同协议详细信息
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(psyConsultContractService.getOne(id));
    }

    /**
     * 新增咨询师合同协议
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:add')")
    @Log(title = "咨询师合同协议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PsyConsultContract entity)
    {
        return toAjax(psyConsultContractService.add(entity));
    }

    /**
     * 修改咨询师合同协议
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:edit')")
    @Log(title = "咨询师合同协议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PsyConsultContract entity)
    {
        return toAjax(psyConsultContractService.update(entity));
    }
    /**
     * 修改咨询师合同协议
     */
    @PreAuthorize("@ss.hasPermi('contract:contract:edit')")
    @Log(title = "咨询师合同协议", businessType = BusinessType.UPDATE)
    @PostMapping ("/relaunch")
    public AjaxResult relaunch(@RequestBody PsyConsultContract entity)
    {
        return toAjax(psyConsultContractService.relaunch(entity));
    }

}
