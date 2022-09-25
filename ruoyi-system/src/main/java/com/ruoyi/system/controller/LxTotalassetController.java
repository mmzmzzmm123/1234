package com.ruoyi.system.controller;

import java.sql.Date;
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
import com.ruoyi.system.domain.LxTotalasset;
import com.ruoyi.system.service.ILxTotalassetService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 总资产走势Controller
 * 
 * @author ruoyi
 * @date 2022-06-19
 */
@RestController
@RequestMapping("/invest/totalasset")
public class LxTotalassetController extends BaseController
{
    @Autowired
    private ILxTotalassetService lxTotalassetService;

    /**
     * 查询总资产走势列表
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:list')")
    @GetMapping("/list")
    public TableDataInfo list(LxTotalasset lxTotalasset)
    {
        startPage();
        List<LxTotalasset> list = lxTotalassetService.selectLxTotalassetList(lxTotalasset);
        return getDataTable(list);
    }

    /**
     * 导出总资产走势列表
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:export')")
    @Log(title = "总资产走势", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, LxTotalasset lxTotalasset)
    {
        List<LxTotalasset> list = lxTotalassetService.selectLxTotalassetList(lxTotalasset);
        ExcelUtil<LxTotalasset> util = new ExcelUtil<LxTotalasset>(LxTotalasset.class);
        util.exportExcel(response, list, "总资产走势数据");
    }

    /**
     * 获取总资产走势详细信息
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:query')")
    @GetMapping(value = "/{riqi}")
    public AjaxResult getInfo(@PathVariable("riqi") Date riqi)
    {
        return AjaxResult.success(lxTotalassetService.selectLxTotalassetByRiqi(riqi));
    }

    /**
     * 新增总资产走势
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:add')")
    @Log(title = "总资产走势", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LxTotalasset lxTotalasset)
    {
        return toAjax(lxTotalassetService.insertLxTotalasset(lxTotalasset));
    }

    /**
     * 修改总资产走势
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:edit')")
    @Log(title = "总资产走势", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LxTotalasset lxTotalasset)
    {
        return toAjax(lxTotalassetService.updateLxTotalasset(lxTotalasset));
    }

    /**
     * 删除总资产走势
     */
    @PreAuthorize("@ss.hasPermi('invest:totalasset:remove')")
    @Log(title = "总资产走势", businessType = BusinessType.DELETE)
	@DeleteMapping("/{riqis}")
    public AjaxResult remove(@PathVariable Date[] riqis)
    {
        return toAjax(lxTotalassetService.deleteLxTotalassetByRiqis(riqis));
    }
}
