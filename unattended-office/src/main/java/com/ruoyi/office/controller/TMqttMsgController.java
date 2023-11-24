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
import com.ruoyi.office.domain.TMqttMsg;
import com.ruoyi.office.service.ITMqttMsgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * mqtt消息发送接收Controller
 * 
 * @author ruoyi
 * @date 2023-11-20
 */
@RestController
@RequestMapping("/office/mqttMsg")
public class TMqttMsgController extends BaseController
{
    @Autowired
    private ITMqttMsgService tMqttMsgService;

    /**
     * 查询mqtt消息发送接收列表
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMqttMsg tMqttMsg)
    {
        startPage();
        List<TMqttMsg> list = tMqttMsgService.selectTMqttMsgList(tMqttMsg);
        return getDataTable(list);
    }

    /**
     * 导出mqtt消息发送接收列表
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:export')")
    @Log(title = "mqtt消息发送接收", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMqttMsg tMqttMsg)
    {
        List<TMqttMsg> list = tMqttMsgService.selectTMqttMsgList(tMqttMsg);
        ExcelUtil<TMqttMsg> util = new ExcelUtil<TMqttMsg>(TMqttMsg.class);
        util.exportExcel(response, list, "mqtt消息发送接收数据");
    }

    /**
     * 获取mqtt消息发送接收详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tMqttMsgService.selectTMqttMsgById(id));
    }

    /**
     * 新增mqtt消息发送接收
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:add')")
    @Log(title = "mqtt消息发送接收", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMqttMsg tMqttMsg)
    {
        return toAjax(tMqttMsgService.insertTMqttMsg(tMqttMsg));
    }

    /**
     * 修改mqtt消息发送接收
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:edit')")
    @Log(title = "mqtt消息发送接收", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMqttMsg tMqttMsg)
    {
        return toAjax(tMqttMsgService.updateTMqttMsg(tMqttMsg));
    }

    /**
     * 删除mqtt消息发送接收
     */
    @PreAuthorize("@ss.hasPermi('office:mqttMsg:remove')")
    @Log(title = "mqtt消息发送接收", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tMqttMsgService.deleteTMqttMsgByIds(ids));
    }
}
