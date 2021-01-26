package com.stdiet.web.controller.custom;

import java.util.ArrayList;
import java.util.List;

import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.domain.SysPhysicalSigns;

import com.stdiet.common.utils.bean.ObjectUtils;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.dto.request.CustomerInvestigateRequest;
import com.stdiet.custom.dto.response.CustomerListResponse;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.service.ISysCustomerService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 客户体征信息Controller
 *
 * @author xzj
 * @date 2021-01-03
 */
@RestController
@RequestMapping("/custom/customer")
public class SysCustomerController extends BaseController
{
    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysCustomerPhysicalSignsService sysCustomerPhysicalSignsService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomerPhysicalSigns sysCustomerPhysicalSigns)
    {
        startPage();
        List<SysCustomerPhysicalSigns> list = sysCustomerPhysicalSignsService.selectSysCustomerAndSignList(sysCustomerPhysicalSigns);
        if(list != null && list.size() > 0){
            for(SysCustomerPhysicalSigns sysCus : list){
                if(StringUtils.isNotEmpty(sysCus.getPhone())){
                    sysCus.setPhone(StringUtils.hiddenPhoneNumber(sysCus.getPhone()));
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:export')")
    @Log(title = "客户体征", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomerPhysicalSigns sysCustomerPhysicalSigns) throws Exception
    {
        List<SysCustomerPhysicalSigns> list = sysCustomerPhysicalSignsService.selectSysCustomerAndSignList(sysCustomerPhysicalSigns);
        List<CustomerListResponse> responsesList = new ArrayList<>();
        CustomerListResponse customerListResponse = null;
        for(SysCustomerPhysicalSigns sysCus : list){
            customerListResponse = ObjectUtils.getObjectByObject(sysCus, CustomerListResponse.class);
            customerListResponse.setCreateTime(sysCus.getCreateTime());
            if(StringUtils.isNotEmpty(sysCus.getPhone())){
                customerListResponse.setPhone(StringUtils.hiddenPhoneNumber(sysCus.getPhone()));
            }
            StringBuilder signStr = new StringBuilder();
            if(sysCus.getSignList() != null && sysCus.getSignList().size() > 0){
                int i = 0;
                for (SysPhysicalSigns s : sysCus.getSignList()) {
                    signStr.append((i != 0 ? "，" : "") + s.getName());
                    i++;
                }
            }
            customerListResponse.setPhysicalSignsId(signStr.toString());
            responsesList.add(customerListResponse);
        }
        ExcelUtil<CustomerListResponse> util = new ExcelUtil<CustomerListResponse>(CustomerListResponse.class);
        return util.exportExcel(responsesList, "客户体征数据");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysCustomerPhysicalSignsService.selectSysCustomerPhysicalSignsById(id));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:add')")
    @Log(title = "客户体征", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerInvestigateRequest customerInvestigateRequest) throws Exception
    {
        return sysCustomerPhysicalSignsService.addOrupdateCustomerAndSign(customerInvestigateRequest);
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:edit')")
    @Log(title = "客户体征", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerInvestigateRequest customerInvestigateRequest) throws Exception
    {
        return sysCustomerPhysicalSignsService.addOrupdateCustomerAndSign(customerInvestigateRequest);
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:remove')")
    @Log(title = "客户体征", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCustomerPhysicalSignsService.deleteSysCustomerPhysicalSignsByIds(ids));
    }

    /**
     * 根据手机号查看用户体征
     */
    @GetMapping("/getCustomerAndSignByPhone")
    @PreAuthorize("@ss.hasPermi('custom:customer:query')")
    public AjaxResult getCustomerAndSignByPhone(@RequestParam("phone")String phone)
    {
        SysCustomerPhysicalSigns  sysCustomer = null;
        if(StringUtils.isNotEmpty(phone)){
           sysCustomer = sysCustomerPhysicalSignsService.selectSysCustomerAndSignByPhone(phone);
        }
        return AjaxResult.success(sysCustomer);
    }
}