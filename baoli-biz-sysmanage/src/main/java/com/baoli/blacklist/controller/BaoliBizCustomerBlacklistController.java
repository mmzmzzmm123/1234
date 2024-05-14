package com.baoli.blacklist.controller;

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
import com.baoli.blacklist.domain.BaoliBizCustomerBlacklist;
import com.baoli.blacklist.service.IBaoliBizCustomerBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户黑名单Controller
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/blacklist/customerBlacklist")
public class BaoliBizCustomerBlacklistController extends BaseController
{
    @Autowired
    private IBaoliBizCustomerBlacklistService baoliBizCustomerBlacklistService;

    /**
     * 查询客户黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        startPage();
        List<BaoliBizCustomerBlacklist> list = baoliBizCustomerBlacklistService.selectBaoliBizCustomerBlacklistList(baoliBizCustomerBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出客户黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:export')")
    @Log(title = "客户黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        List<BaoliBizCustomerBlacklist> list = baoliBizCustomerBlacklistService.selectBaoliBizCustomerBlacklistList(baoliBizCustomerBlacklist);
        ExcelUtil<BaoliBizCustomerBlacklist> util = new ExcelUtil<BaoliBizCustomerBlacklist>(BaoliBizCustomerBlacklist.class);
        util.exportExcel(response, list, "客户黑名单数据");
    }

    /**
     * 获取客户黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizCustomerBlacklistService.selectBaoliBizCustomerBlacklistById(id));
    }

    /**
     * 新增客户黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:add')")
    @Log(title = "客户黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        return toAjax(baoliBizCustomerBlacklistService.insertBaoliBizCustomerBlacklist(baoliBizCustomerBlacklist));
    }

    /**
     * 修改客户黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:edit')")
    @Log(title = "客户黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        return toAjax(baoliBizCustomerBlacklistService.updateBaoliBizCustomerBlacklist(baoliBizCustomerBlacklist));
    }

    /**
     * 删除客户黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:customerBlacklist:remove')")
    @Log(title = "客户黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizCustomerBlacklistService.deleteBaoliBizCustomerBlacklistByIds(ids));
    }
}
