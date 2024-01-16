package com.ruoyi.web.controller.onethinker;

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

import com.ruoyi.onethinker.domain.SubscribeMsgCtrl;
import com.ruoyi.onethinker.service.ISubscribeMsgCtrlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订阅消息控制Controller
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
@RestController
@RequestMapping("/onethinker/ctrl")
public class SubscribeMsgCtrlController extends BaseController {
    @Autowired
    private ISubscribeMsgCtrlService subscribeMsgCtrlService;

    /**
     * 查询订阅消息控制列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:list')")
    @GetMapping("/list")
    public TableDataInfo list(SubscribeMsgCtrl subscribeMsgCtrl) {
        startPage();
        List<SubscribeMsgCtrl> list = subscribeMsgCtrlService.selectSubscribeMsgCtrlList(subscribeMsgCtrl);
        return getDataTable(list);
    }

    /**
     * 导出订阅消息控制列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:export')")
    @Log(title = "订阅消息控制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SubscribeMsgCtrl subscribeMsgCtrl) {
        List<SubscribeMsgCtrl> list = subscribeMsgCtrlService.selectSubscribeMsgCtrlList(subscribeMsgCtrl);
        ExcelUtil<SubscribeMsgCtrl> util = new ExcelUtil<SubscribeMsgCtrl>(SubscribeMsgCtrl. class);
        util.exportExcel(response, list, "订阅消息控制数据");
    }

    /**
     * 获取订阅消息控制详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(subscribeMsgCtrlService.selectSubscribeMsgCtrlById(id));
    }

    /**
     * 新增订阅消息控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:add')")
    @Log(title = "订阅消息控制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SubscribeMsgCtrl subscribeMsgCtrl) {
        return toAjax(subscribeMsgCtrlService.insertSubscribeMsgCtrl(subscribeMsgCtrl));
    }

    /**
     * 修改订阅消息控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:edit')")
    @Log(title = "订阅消息控制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SubscribeMsgCtrl subscribeMsgCtrl) {
        return toAjax(subscribeMsgCtrlService.updateSubscribeMsgCtrl(subscribeMsgCtrl));
    }

    /**
     * 删除订阅消息控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:remove')")
    @Log(title = "订阅消息控制", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(subscribeMsgCtrlService.deleteSubscribeMsgCtrlByIds(ids));
    }
}
