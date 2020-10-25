package com.ruoyi.web.controller.custom;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.Contract;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.custom.domain.SysContract;
import com.ruoyi.custom.service.ISysContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
            AjaxResult ajax = AjaxResult.success();
//            ajax.put("url", "/login");
            ajax.put("data", id);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 合同签单
     */
    @PostMapping("/sign")
    public AjaxResult signContract(@RequestBody Contract contract) throws Exception {
        try {
            AjaxResult ajax = AjaxResult.success();
            ajax.put("result", "hi");
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
