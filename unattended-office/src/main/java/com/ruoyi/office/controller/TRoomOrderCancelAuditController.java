package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.office.domain.vo.RefundAuditVo;
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
import com.ruoyi.office.domain.TRoomOrderCancelAudit;
import com.ruoyi.office.service.ITRoomOrderCancelAuditService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 预约取消审核Controller
 *
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/office/roomcancelaudit")
public class TRoomOrderCancelAuditController extends BaseController {
    @Autowired
    private ITRoomOrderCancelAuditService tRoomOrderCancelAuditService;

    /**
     * 查询预约取消审核列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:list')")
    @GetMapping("/list")
    public TableDataInfo list(RefundAuditVo tRoomOrderCancelAudit) {
        startPage();
//        List<TRoomOrderCancelAudit> list = tRoomOrderCancelAuditService.selectTRoomOrderCancelAuditList(tRoomOrderCancelAudit);
        List<RefundAuditVo> list = tRoomOrderCancelAuditService.selectCancelAuditInfoList(tRoomOrderCancelAudit);
        return getDataTable(list);
    }

    /**
     * 导出预约取消审核列表
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:export')")
    @Log(title = "预约取消审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        List<TRoomOrderCancelAudit> list = tRoomOrderCancelAuditService.selectTRoomOrderCancelAuditList(tRoomOrderCancelAudit);
        ExcelUtil<TRoomOrderCancelAudit> util = new ExcelUtil<TRoomOrderCancelAudit>(TRoomOrderCancelAudit.class);
        util.exportExcel(response, list, "预约取消审核数据");
    }

    /**
     * 获取预约取消审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tRoomOrderCancelAuditService.selectTRoomOrderCancelAuditById(id));
    }

    /**
     * 新增预约取消审核
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:add')")
    @Log(title = "预约取消审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        return toAjax(tRoomOrderCancelAuditService.insertTRoomOrderCancelAudit(tRoomOrderCancelAudit));
    }

    /**
     * 修改预约取消审核
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:edit')")
    @Log(title = "预约取消审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        return toAjax(tRoomOrderCancelAuditService.updateTRoomOrderCancelAudit(tRoomOrderCancelAudit));
    }

    /**
     * 删除预约取消审核
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:remove')")
    @Log(title = "预约取消审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tRoomOrderCancelAuditService.deleteTRoomOrderCancelAuditByIds(ids));
    }

    /**
     * 修改预约取消审核
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:edit')")
    @Log(title = "审核通过", businessType = BusinessType.UPDATE)
    @PutMapping("/approve")
    public AjaxResult approve(@RequestBody TRoomOrderCancelAudit tRoomOrderCancelAudit) {

        TRoomOrderCancelAudit audit = tRoomOrderCancelAuditService.selectTRoomOrderCancelAuditById(tRoomOrderCancelAudit.getId());
        if (audit.getStatus() == 1) {
            throw new ServiceException("已经通过的单据不能重复操作");
        }
        return AjaxResult.success(tRoomOrderCancelAuditService.approve(tRoomOrderCancelAudit));
    }

    /**
     * 修改预约取消审核
     */
    @PreAuthorize("@ss.hasPermi('office:roomcancelaudit:edit')")
    @Log(title = "审核退回", businessType = BusinessType.UPDATE)
    @PutMapping("/back")
    public AjaxResult auditBack(@RequestBody TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        TRoomOrderCancelAudit audit = tRoomOrderCancelAuditService.selectTRoomOrderCancelAuditById(tRoomOrderCancelAudit.getId());
        if (audit.getStatus() == 1) {
            throw new ServiceException("已经通过的单据无法退回");
        }
        return toAjax(tRoomOrderCancelAuditService.updateTRoomOrderCancelAudit(tRoomOrderCancelAudit));
    }
}
