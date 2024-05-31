package com.onethinker.admin.controller.activity;

import com.onethinker.activity.domain.SubscribeMsgCtrl;
import com.onethinker.activity.service.ISubscribeMsgCtrlService;
import com.onethinker.common.annotation.Log;
import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.page.TableDataInfo;
import com.onethinker.common.enums.BusinessType;
import com.onethinker.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订阅消息
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
    @GetMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/list")
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
    @PostMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, SubscribeMsgCtrl subscribeMsgCtrl) {
        List<SubscribeMsgCtrl> list = subscribeMsgCtrlService.selectSubscribeMsgCtrlList(subscribeMsgCtrl);
        ExcelUtil<SubscribeMsgCtrl> util = new ExcelUtil<SubscribeMsgCtrl>(SubscribeMsgCtrl.class);
        util.exportExcel(response, list, "订阅消息控制数据");
    }

    /**
     * 获取订阅消息控制详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:query')")
    @GetMapping(value = ServicePathConstant.PREFIX_SERVICE_PATH +  "/{id}")
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
    @DeleteMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(subscribeMsgCtrlService.deleteSubscribeMsgCtrlByIds(ids));
    }
}
