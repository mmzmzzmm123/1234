package com.ruoyi.office.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.mqtt.MqttSendClient;
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
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.service.ITEquipmentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备列表Controller
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@RestController
@RequestMapping("/office/equipment")
public class TEquipmentController extends BaseController {
    @Autowired
    private ITEquipmentService tEquipmentService;

    /**
     * 查询设备列表列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(TEquipment tEquipment) {
//        if (!SecurityUtils.getUsername().equalsIgnoreCase("admin"))
        tEquipment.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TEquipment> list = tEquipmentService.selectTEquipmentList(tEquipment);
        return getDataTable(list);
    }

    /**
     * 导出设备列表列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:export')")
    @Log(title = "设备列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TEquipment tEquipment) {
        List<TEquipment> list = tEquipmentService.selectTEquipmentList(tEquipment);
        ExcelUtil<TEquipment> util = new ExcelUtil<TEquipment>(TEquipment.class);
        util.exportExcel(response, list, "设备列表数据");
    }

    /**
     * 获取设备列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(tEquipmentService.selectTEquipmentById(id));
    }

    /**
     * 新增设备列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:add')")
    @Log(title = "设备列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TEquipment tEquipment) {
        tEquipment.setCreateBy(SecurityUtils.getUserId() + "");

        return toAjax(tEquipmentService.insertTEquipment(tEquipment));
    }

    /**
     * 修改设备列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:edit')")
    @Log(title = "设备列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TEquipment tEquipment) {
        tEquipment.setUpdateBy(SecurityUtils.getUserId() + "");
        return toAjax(tEquipmentService.updateTEquipment(tEquipment));
    }

    /**
     * 删除设备列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:remove')")
    @Log(title = "设备列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tEquipmentService.deleteTEquipmentByIds(ids));
    }

    @PutMapping("/setting")
    public AjaxResult setDevice(@RequestBody TEquipment tEquipment) {
        MqttSendClient sendClient = new MqttSendClient();
        TEquipment qry = tEquipmentService.selectTEquipmentById(tEquipment.getId());
        if ("Y".equalsIgnoreCase(tEquipment.getOnOff())) {
            sendClient.publish(qry.getEquipControl(), "a1");
        } else if ("N".equalsIgnoreCase(tEquipment.getOnOff())) {
            sendClient.publish(qry.getEquipControl(), "b1");
        }

        tEquipment.setUpdateBy(SecurityUtils.getUserId() + "");
        return toAjax(tEquipmentService.updateTEquipment(tEquipment));
    }
}
