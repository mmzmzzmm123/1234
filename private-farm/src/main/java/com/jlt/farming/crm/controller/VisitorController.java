package com.jlt.farming.crm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jlt.farming.crm.domain.Visitor;
import com.jlt.farming.crm.service.IVisitorService;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 访客信息Controller
 * 
 * @author ruoyi
 * @date 2022-02-08
 */
@RestController
@RequestMapping("/crm/visitor")
public class VisitorController extends BaseController
{
    @Autowired
    private IVisitorService VisitorService;

    /**
     * 查询访客信息列表
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Visitor Visitor)
    {
        startPage();
        List<Visitor> list = VisitorService.selectVisitorList(Visitor);
        return getDataTable(list);
    }

    /**
     * 导出访客信息列表
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:export')")
    @Log(title = "访客信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Visitor Visitor)
    {
        List<Visitor> list = VisitorService.selectVisitorList(Visitor);
        ExcelUtil<Visitor> util = new ExcelUtil<Visitor>(Visitor.class);
        util.exportExcel(response, list, "访客信息数据");
    }

    /**
     * 获取访客信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:query')")
    @GetMapping(value = "/{visitorId}")
    public AjaxResult getInfo(@PathVariable("visitorId") Long visitorId)
    {
        return AjaxResult.success(VisitorService.selectVisitorByVisitorId(visitorId));
    }

    /**
     * 新增访客信息
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:add')")
    @Log(title = "访客信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Visitor Visitor)
    {
        return toAjax(VisitorService.insertVisitor(Visitor));
    }

    /**
     * 修改访客信息
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:edit')")
    @Log(title = "访客信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Visitor Visitor)
    {
        return toAjax(VisitorService.updateVisitor(Visitor));
    }

    /**
     * 删除访客信息
     */
    @PreAuthorize("@ss.hasPermi('crm:visitor:remove')")
    @Log(title = "访客信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{visitorIds}")
    public AjaxResult remove(@PathVariable Long[] visitorIds)
    {
        return toAjax(VisitorService.deleteVisitorByVisitorIds(visitorIds));
    }
}
