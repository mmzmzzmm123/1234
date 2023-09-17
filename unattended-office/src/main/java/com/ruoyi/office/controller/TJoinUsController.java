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
import com.ruoyi.office.domain.TJoinUs;
import com.ruoyi.office.service.ITJoinUsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 我要加盟Controller
 * 
 * @author ruoyi
 * @date 2023-09-17
 */
@RestController
@RequestMapping("/office/joinus")
public class TJoinUsController extends BaseController
{
    @Autowired
    private ITJoinUsService tJoinUsService;

    /**
     * 查询我要加盟列表
     */
    @PreAuthorize("@ss.hasPermi('office:joinus:list')")
    @GetMapping("/list")
    public TableDataInfo list(TJoinUs tJoinUs)
    {
        startPage();
        List<TJoinUs> list = tJoinUsService.selectTJoinUsList(tJoinUs);
        return getDataTable(list);
    }

    /**
     * 导出我要加盟列表
     */
    @PreAuthorize("@ss.hasPermi('office:joinus:export')")
    @Log(title = "我要加盟", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TJoinUs tJoinUs)
    {
        List<TJoinUs> list = tJoinUsService.selectTJoinUsList(tJoinUs);
        ExcelUtil<TJoinUs> util = new ExcelUtil<TJoinUs>(TJoinUs.class);
        util.exportExcel(response, list, "我要加盟数据");
    }

    /**
     * 获取我要加盟详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:joinus:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tJoinUsService.selectTJoinUsById(id));
    }

    /**
     * 新增我要加盟
     */
//    @PreAuthorize("@ss.hasPermi('office:joinus:add')")
    @Log(title = "我要加盟", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TJoinUs tJoinUs)
    {
        return toAjax(tJoinUsService.insertTJoinUs(tJoinUs));
    }

    /**
     * 修改我要加盟
     */
//    @PreAuthorize("@ss.hasPermi('office:joinus:edit')")
    @Log(title = "我要加盟", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TJoinUs tJoinUs)
    {
        return toAjax(tJoinUsService.updateTJoinUs(tJoinUs));
    }

    /**
     * 删除我要加盟
     */
    @PreAuthorize("@ss.hasPermi('office:joinus:remove')")
    @Log(title = "我要加盟", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tJoinUsService.deleteTJoinUsByIds(ids));
    }
}
