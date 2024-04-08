package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BlSysBank;
import com.baoli.sysmanage.service.IBlSysBankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 银行管理Controller
 * 
 * @author niujs
 * @date 2024-04-08
 */
@RestController
@RequestMapping("/sysmanage/bank")
public class BlSysBankController extends BaseController
{
    @Autowired
    private IBlSysBankService blSysBankService;

    /**
     * 查询银行管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysBank blSysBank)
    {
        startPage();
        List<BlSysBank> list = blSysBankService.selectBlSysBankList(blSysBank);
        return getDataTable(list);
    }

    /**
     * 导出银行管理列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:export')")
    @Log(title = "银行管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysBank blSysBank)
    {
        List<BlSysBank> list = blSysBankService.selectBlSysBankList(blSysBank);
        ExcelUtil<BlSysBank> util = new ExcelUtil<BlSysBank>(BlSysBank.class);
        util.exportExcel(response, list, "银行管理数据");
    }

    /**
     * 获取银行管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysBankService.selectBlSysBankById(id));
    }

    /**
     * 新增银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:add')")
    @Log(title = "银行管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysBank blSysBank)
    {
        return toAjax(blSysBankService.insertBlSysBank(blSysBank));
    }

    /**
     * 修改银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:edit')")
    @Log(title = "银行管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysBank blSysBank)
    {
        return toAjax(blSysBankService.updateBlSysBank(blSysBank));
    }

    /**
     * 删除银行管理
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:bank:remove')")
    @Log(title = "银行管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysBankService.deleteBlSysBankByIds(ids));
    }
}
