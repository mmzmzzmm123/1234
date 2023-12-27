package com.ruoyi.web.controller.system;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.system.domain.Installmentpayment;
import com.ruoyi.system.service.IInstallmentpaymentService;
import com.ruoyi.web.core.config.UtilConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.Srchecksheet;
import com.ruoyi.system.service.ISrchecksheetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2023-12-20
 */
@RestController
@RequestMapping("/system/srchecksheet")
public class SrchecksheetController extends BaseController
{
    @Autowired
    private IInstallmentpaymentService installmentpaymentService;

    @Autowired
    private ISrchecksheetService srchecksheetService;

    @Autowired
    private UtilConfig utilConfig;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:list')")
    @GetMapping("/list")
    public TableDataInfo list(Srchecksheet srchecksheet)
    {
        startPage();
        List<Srchecksheet> list = srchecksheetService.selectSrchecksheetList(srchecksheet);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Srchecksheet srchecksheet)
    {
        List<Srchecksheet> list = srchecksheetService.selectSrchecksheetList(srchecksheet);
        ExcelUtil<Srchecksheet> util = new ExcelUtil<Srchecksheet>(Srchecksheet.class);
        util.exportExcel(response, list, "报销单数据");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:query')")
    @GetMapping("/getInfo/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(srchecksheetService.selectSrchecksheetById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Srchecksheet srchecksheet) {
        return toAjax(srchecksheetService.insertSrchecksheet(srchecksheet));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Srchecksheet srchecksheet)
    {
        return toAjax(srchecksheetService.updateSrchecksheet(srchecksheet));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:srchecksheet:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(srchecksheetService.deleteSrchecksheetByIds(ids));
    }
}
