package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysCustomerPhysicalSigns;
import com.stdiet.custom.service.ISysCustomerHealthyService;
import com.stdiet.custom.service.ISysCustomerPhysicalSignsService;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户体征信息Controller
 *
 * @author xzj
 * @date 2021-01-03
 */
@RestController
@RequestMapping("/custom/customer")
public class SysCustomerController extends BaseController {
    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysCustomerPhysicalSignsService sysCustomerPhysicalSignsService;

    @Autowired
    private ISysCustomerHealthyService sysCustomerHealthyService;

    /**
     * 查询客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCustomer sysCustomer) {
        startPage();
        String remark = SecurityUtils.getLoginUser().getUser().getRemark();
        if (StringUtils.isNotEmpty(remark) && remark.contains("|") && sysCustomer.getChannelId() == null) {
            sysCustomer.setChannels(remark.split("\\|"));
        }
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        if (list != null && list.size() > 0) {
            for (SysCustomer sysCus : list) {
                if (StringUtils.isNotEmpty(sysCus.getPhone())) {
                    sysCus.setPhone(StringUtils.hiddenPhoneNumber(sysCus.getPhone()));
                    sysCus.setEncId(sysCus.getId() != null ? AesUtils.encrypt(sysCus.getId() + "", null) : "");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出客户信息列表
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:export')")
    @Log(title = "客户档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysCustomer sysCustomer) throws Exception {
        List<SysCustomer> list = sysCustomerService.selectSysCustomerList(sysCustomer);
        ExcelUtil<SysCustomer> util = new ExcelUtil<SysCustomer>(SysCustomer.class);
        return util.exportExcel(list, "customerCenter");
    }

    /**
     * 获取客户信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysCustomerService.selectSysCustomerById(id));
    }

    /**
     * 新增客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:add')")
    @Log(title = "客户档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCustomer sysCustomer) throws Exception {
        if (!sysCustomerService.isCustomerExistByPhone(sysCustomer)) {
            return toAjax(sysCustomerService.insertSysCustomer(sysCustomer));
        }
        return AjaxResult.error("该手机号客户已存在");
    }

    /**
     * 修改客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:edit')")
    @Log(title = "客户档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCustomer sysCustomer) throws Exception {
        if (!sysCustomerService.isCustomerExistByPhone(sysCustomer)) {
            return toAjax(sysCustomerService.updateSysCustomer(sysCustomer));
        }
        return AjaxResult.error("该手机号客户已存在");
    }

    /**
     * 删除客户信息
     */
    @PreAuthorize("@ss.hasPermi('custom:customer:remove')")
    @Log(title = "客户档案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysCustomerService.deleteSysCustomerByIds(ids));
    }

    /**
     * 根据手机号查看用户体征
     */
    @GetMapping("/getCustomerAndSignByPhone")
    @PreAuthorize("@ss.hasPermi('custom:customer:query')")
    public AjaxResult getCustomerAndSignByPhone(@RequestParam("phone") String phone) {
        SysCustomerPhysicalSigns sysCustomer = null;
        if (StringUtils.isNotEmpty(phone)) {
            sysCustomer = sysCustomerPhysicalSignsService.selectSysCustomerAndSignByPhone(phone);
        }
        return AjaxResult.success(sysCustomer);
    }

    /**
     * 根据客户ID获取体征或健康评估信息，优先健康评估信息
     *
     * @param id 客户ID
     * @return
     */
    @GetMapping("/physicalSigns/{id}")
    public AjaxResult getPhysicalSignsById(@PathVariable("id") Long id) {
        return AjaxResult.success(sysCustomerService.getPhysicalSignsById(id));
    }

    /**
     * 根据客户ID删除对应体征信息或健康评估信息
     *
     * @param customerId 客户ID
     * @return
     */
    @GetMapping("/delCustomerHealthy/{id}")
    public AjaxResult delCustomerHealthy(@PathVariable("id") Long customerId) {
        int signRow = sysCustomerPhysicalSignsService.delCustomerSignByCustomerId(customerId);
        int healthyRow = sysCustomerHealthyService.deleteCustomerHealthyByCustomerId(customerId);
        return toAjax(signRow + healthyRow);
    }
}