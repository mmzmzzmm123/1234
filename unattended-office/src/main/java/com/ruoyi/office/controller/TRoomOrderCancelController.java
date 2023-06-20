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
import com.ruoyi.office.domain.TRoomOrderCancel;
import com.ruoyi.office.service.ITRoomOrderCancelService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预约取消设置Controller
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/office/roomcancel")
public class TRoomOrderCancelController extends BaseController
{
    @Autowired
    private ITRoomOrderCancelService tRoomOrderCancelService;

    /**
     * 查询预约取消设置列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:list')")
    @GetMapping("/list")
    public TableDataInfo list(TRoomOrderCancel tRoomOrderCancel)
    {
        startPage();
        List<TRoomOrderCancel> list = tRoomOrderCancelService.selectTRoomOrderCancelList(tRoomOrderCancel);
        return getDataTable(list);
    }

    /**
     * 导出预约取消设置列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:export')")
    @Log(title = "预约取消设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomOrderCancel tRoomOrderCancel)
    {
        List<TRoomOrderCancel> list = tRoomOrderCancelService.selectTRoomOrderCancelList(tRoomOrderCancel);
        ExcelUtil<TRoomOrderCancel> util = new ExcelUtil<TRoomOrderCancel>(TRoomOrderCancel.class);
        util.exportExcel(response, list, "预约取消设置数据");
    }

    /**
     * 获取预约取消设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tRoomOrderCancelService.selectTRoomOrderCancelById(id));
    }

    /**
     * 新增预约取消设置
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:add')")
    @Log(title = "预约取消设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomOrderCancel tRoomOrderCancel)
    {
        return toAjax(tRoomOrderCancelService.insertTRoomOrderCancel(tRoomOrderCancel));
    }

    /**
     * 修改预约取消设置
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:edit')")
    @Log(title = "预约取消设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomOrderCancel tRoomOrderCancel)
    {
        return toAjax(tRoomOrderCancelService.updateTRoomOrderCancel(tRoomOrderCancel));
    }

    /**
     * 删除预约取消设置
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancel:remove')")
    @Log(title = "预约取消设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tRoomOrderCancelService.deleteTRoomOrderCancelByIds(ids));
    }
}
