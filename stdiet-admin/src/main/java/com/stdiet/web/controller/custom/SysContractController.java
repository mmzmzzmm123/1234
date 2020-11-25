package com.stdiet.web.controller.custom;

import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysContract;
import com.stdiet.custom.page.PdfProcessInfo;
import com.stdiet.custom.service.ISysContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同Controller
 *
 * @author wonder
 * @date 2020-10-23
 */
@RestController
@RequestMapping("/custom/contract")
public class SysContractController extends BaseController {
    @Autowired
    private ISysContractService sysContractService;

    /**
     * 查询合同列表
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysContract sysContract) {
        startPage();
        List<SysContract> list = sysContractService.selectSysContractList(sysContract);
        for (SysContract contract : list) {
            if (contract.getSignName() != null && !contract.getSignName().equals("") && !contract.getName().trim().equals(contract.getSignName())) {
                contract.setName(contract.getName() + "（" + contract.getSignName() + "）");
            }
            if(contract.getPhone() != null && !contract.getPhone().equals("")) {
                contract.setPhone(contract.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出合同列表
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:export')")
    @Log(title = "合同", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysContract sysContract) {
        List<SysContract> list = sysContractService.selectSysContractList(sysContract);
        ExcelUtil<SysContract> util = new ExcelUtil<SysContract>(SysContract.class);
        return util.exportExcel(list, "contract");
    }

    /**
     * 获取合同详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysContractService.selectSysContractById(id));
    }

    /**
     * 新增合同
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:add')")
    @Log(title = "合同", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysContract sysContract) {
        return toAjax(sysContractService.insertSysContract(sysContract));
    }

    /**
     * 修改合同
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:edit')")
    @Log(title = "合同", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysContract sysContract) {
        return toAjax(sysContractService.updateSysContract(sysContract));
    }

    /**
     * 删除合同
     */
    @PreAuthorize("@ss.hasPermi('custom:contract:remove')")
    @Log(title = "合同", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sysContractService.deleteSysContractByIds(ids));
    }


    @GetMapping("/file/{id}")
    public AjaxResult getfile(@PathVariable long id) {
        AjaxResult ajax = AjaxResult.success();
        SysContract contract = sysContractService.selectSysContractById(id);
        if (contract.getStatus().equals("yes")) {
            ajax.put("url", contract.getPath());
        } else {
            Map<String, String> data = new HashMap<>();
            data.put("id", contract.getId().toString());
            data.put("amount", contract.getAmount().toString());
            data.put("serveTime", contract.getServeTime() + "");
            data.put("tutor", contract.getTutor());
            data.put("servePromise", contract.getServePromise());
            data.put("remark", contract.getRemark());
            ajax.put("data", data);
        }
        return ajax;
    }

    /**
     * 合同签单
     */
    @PostMapping("/sign")
    public AjaxResult signContract(@RequestBody SysContract sysContract) {
        String path = "/file/" + sysContract.getId() + ".pdf";
        sysContract.setPath(path);
        PdfProcessInfo result = sysContractService.signContract(sysContract);
        if (result.getCode() == 1) {
            sysContract.setStatus("yes");
            sysContractService.updateSysContract(sysContract);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", path);
            return ajax;
        } else {
            return AjaxResult.error(result.getMsg());
        }
    }
}
