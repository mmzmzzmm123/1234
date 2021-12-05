package com.ruoyi.carpool.controller;

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
import com.ruoyi.carpool.domain.PBlacklist;
import com.ruoyi.carpool.service.IPBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 黑名单Controller
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
@RestController
@RequestMapping("/carpool/blacklist")
public class PBlacklistController extends BaseController
{
    @Autowired
    private IPBlacklistService pBlacklistService;

    /**
     * 查询黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(PBlacklist pBlacklist)
    {
        startPage();
        List<PBlacklist> list = pBlacklistService.selectPBlacklistList(pBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:export')")
    @Log(title = "黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PBlacklist pBlacklist)
    {
        List<PBlacklist> list = pBlacklistService.selectPBlacklistList(pBlacklist);
        ExcelUtil<PBlacklist> util = new ExcelUtil<PBlacklist>(PBlacklist.class);
        util.exportExcel(response, list, "黑名单数据");
    }

    /**
     * 获取黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(pBlacklistService.selectPBlacklistById(id));
    }

    /**
     * 新增黑名单
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:add')")
    @Log(title = "黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PBlacklist pBlacklist)
    {
        return toAjax(pBlacklistService.insertPBlacklist(pBlacklist));
    }

    /**
     * 修改黑名单
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:edit')")
    @Log(title = "黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PBlacklist pBlacklist)
    {
        return toAjax(pBlacklistService.updatePBlacklist(pBlacklist));
    }

    /**
     * 删除黑名单
     */
    @PreAuthorize("@ss.hasPermi('carpool:blacklist:remove')")
    @Log(title = "黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(pBlacklistService.deletePBlacklistByIds(ids));
    }
}
