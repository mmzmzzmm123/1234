package com.ruoyi.web.controller.message;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.message.domain.MessageTemplate;
import com.ruoyi.message.service.IMessageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 消息模板Controller
 * 
 * @author lixin
 * @date 2022-12-16
 */
@RestController
@RequestMapping("/message/template")
public class MessageTemplateController extends BaseController
{
    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 查询消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(MessageTemplate messageTemplate)
    {
        startPage();
        List<MessageTemplate> list = messageTemplateService.selectMessageTemplateList(messageTemplate);
        return getDataTable(list);
    }

    /**
     * 导出消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:export')")
    @Log(title = "消息模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MessageTemplate messageTemplate)
    {
        List<MessageTemplate> list = messageTemplateService.selectMessageTemplateList(messageTemplate);
        ExcelUtil<MessageTemplate> util = new ExcelUtil<MessageTemplate>(MessageTemplate.class);
        util.exportExcel(response, list, "消息模板数据");
    }

    /**
     * 获取消息模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:template:query')")
    @GetMapping(value = "/{msgTempId}")
    public AjaxResult getInfo(@PathVariable("msgTempId") Long msgTempId)
    {
        return success(messageTemplateService.selectMessageTemplateByMsgTempId(msgTempId));
    }

    /**
     * 新增消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:add')")
    @Log(title = "消息模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageTemplate messageTemplate)
    {
        return toAjax(messageTemplateService.insertMessageTemplate(messageTemplate));
    }

    /**
     * 修改消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:edit')")
    @Log(title = "消息模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageTemplate messageTemplate)
    {
        return toAjax(messageTemplateService.updateMessageTemplate(messageTemplate));
    }

    /**
     * 删除消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:remove')")
    @Log(title = "消息模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{msgTempIds}")
    public AjaxResult remove(@PathVariable Long[] msgTempIds)
    {
        return toAjax(messageTemplateService.deleteMessageTemplateByMsgTempIds(msgTempIds));
    }
}
