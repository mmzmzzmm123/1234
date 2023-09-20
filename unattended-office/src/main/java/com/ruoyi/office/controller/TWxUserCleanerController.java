package com.ruoyi.office.controller;

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
import com.ruoyi.office.domain.TWxUserCleaner;
import com.ruoyi.office.service.ITWxUserCleanerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 保洁员信息Controller
 * 
 * @author ruoyi
 * @date 2023-09-20
 */
@RestController
@RequestMapping("/office/cleaner")
public class TWxUserCleanerController extends BaseController
{
    @Autowired
    private ITWxUserCleanerService tWxUserCleanerService;

    /**
     * 查询保洁员信息列表
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:list')")
    @GetMapping("/list")
    public TableDataInfo list(TWxUserCleaner tWxUserCleaner)
    {
        startPage();
        List<TWxUserCleaner> list = tWxUserCleanerService.selectTWxUserCleanerList(tWxUserCleaner);
        return getDataTable(list);
    }

    /**
     * 导出保洁员信息列表
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:export')")
    @Log(title = "保洁员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TWxUserCleaner tWxUserCleaner)
    {
        List<TWxUserCleaner> list = tWxUserCleanerService.selectTWxUserCleanerList(tWxUserCleaner);
        ExcelUtil<TWxUserCleaner> util = new ExcelUtil<TWxUserCleaner>(TWxUserCleaner.class);
        util.exportExcel(response, list, "保洁员信息数据");
    }

    /**
     * 获取保洁员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tWxUserCleanerService.selectTWxUserCleanerById(id));
    }

    /**
     * 新增保洁员信息
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:add')")
    @Log(title = "保洁员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TWxUserCleaner tWxUserCleaner)
    {
        return toAjax(tWxUserCleanerService.insertTWxUserCleaner(tWxUserCleaner));
    }

    /**
     * 修改保洁员信息
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:edit')")
    @Log(title = "保洁员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TWxUserCleaner tWxUserCleaner)
    {
        return toAjax(tWxUserCleanerService.updateTWxUserCleaner(tWxUserCleaner));
    }

    /**
     * 删除保洁员信息
     */
    @PreAuthorize("@ss.hasPermi('office:cleaner:remove')")
    @Log(title = "保洁员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tWxUserCleanerService.deleteTWxUserCleanerByIds(ids));
    }
}
