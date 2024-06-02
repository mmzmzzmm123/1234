package com.baoli.sysmanage.controller;

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
import com.baoli.sysmanage.domain.BlSysMessage;
import com.baoli.sysmanage.service.IBlSysMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 消息通知Controller
 * 
 * @author niujs
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/sysmanage/message")
public class BlSysMessageController extends BaseController
{
    @Autowired
    private IBlSysMessageService blSysMessageService;

    /**
     * 查询消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(BlSysMessage blSysMessage)
    {
        startPage();
        List<BlSysMessage> list = blSysMessageService.selectBlSysMessageList(blSysMessage);
        return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:export')")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BlSysMessage blSysMessage)
    {
        List<BlSysMessage> list = blSysMessageService.selectBlSysMessageList(blSysMessage);
        ExcelUtil<BlSysMessage> util = new ExcelUtil<BlSysMessage>(BlSysMessage.class);
        util.exportExcel(response, list, "消息通知数据");
    }

    /**
     * 获取消息通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(blSysMessageService.selectBlSysMessageById(id));
    }

    /**
     * 新增消息通知
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:add')")
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BlSysMessage blSysMessage)
    {
        return toAjax(blSysMessageService.insertBlSysMessage(blSysMessage));
    }

    /**
     * 修改消息通知
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:edit')")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BlSysMessage blSysMessage)
    {
        return toAjax(blSysMessageService.updateBlSysMessage(blSysMessage));
    }

    /**
     * 删除消息通知
     */
    @PreAuthorize("@ss.hasPermi('sysmanage:message:remove')")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(blSysMessageService.deleteBlSysMessageByIds(ids));
    }
}
