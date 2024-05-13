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
import com.baoli.blacklist.domain.BaoliBizEnterpriseBlacklist;
import com.baoli.blacklist.service.IBaoliBizEnterpriseBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业黑名单Controller
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
@RestController
@RequestMapping("/blacklist/enterpriseBlacklist")
public class BaoliBizEnterpriseBlacklistController extends BaseController
{
    @Autowired
    private IBaoliBizEnterpriseBlacklistService baoliBizEnterpriseBlacklistService;

    /**
     * 查询企业黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        startPage();
        List<BaoliBizEnterpriseBlacklist> list = baoliBizEnterpriseBlacklistService.selectBaoliBizEnterpriseBlacklistList(baoliBizEnterpriseBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出企业黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:export')")
    @Log(title = "企业黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        List<BaoliBizEnterpriseBlacklist> list = baoliBizEnterpriseBlacklistService.selectBaoliBizEnterpriseBlacklistList(baoliBizEnterpriseBlacklist);
        ExcelUtil<BaoliBizEnterpriseBlacklist> util = new ExcelUtil<BaoliBizEnterpriseBlacklist>(BaoliBizEnterpriseBlacklist.class);
        util.exportExcel(response, list, "企业黑名单数据");
    }

    /**
     * 获取企业黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(baoliBizEnterpriseBlacklistService.selectBaoliBizEnterpriseBlacklistById(id));
    }

    /**
     * 新增企业黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:add')")
    @Log(title = "企业黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        return toAjax(baoliBizEnterpriseBlacklistService.insertBaoliBizEnterpriseBlacklist(baoliBizEnterpriseBlacklist));
    }

    /**
     * 修改企业黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:edit')")
    @Log(title = "企业黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        return toAjax(baoliBizEnterpriseBlacklistService.updateBaoliBizEnterpriseBlacklist(baoliBizEnterpriseBlacklist));
    }

    /**
     * 删除企业黑名单
     */
    @PreAuthorize("@ss.hasPermi('blacklist:enterpriseBlacklist:remove')")
    @Log(title = "企业黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(baoliBizEnterpriseBlacklistService.deleteBaoliBizEnterpriseBlacklistByIds(ids));
    }
}
