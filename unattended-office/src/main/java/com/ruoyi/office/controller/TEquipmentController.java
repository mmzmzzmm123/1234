package com.ruoyi.office.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.mqtt.MqttAcceptClient;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.system.service.ISysDictDataService;
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

    @Autowired
    ISysDictDataService dictDataService;

    @PutMapping("/setting")
    public AjaxResult setDevice(@RequestBody TEquipment tEquipment) {
        MqttSendClient sendClient = new MqttSendClient();
        TEquipment qry = tEquipmentService.selectTEquipmentById(tEquipment.getId());

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type");
        Map<String, String> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getRemark));
        try {
            if ("Y".equalsIgnoreCase(tEquipment.getOnOff())) {
                Map<String, String> msg = new HashMap<>();
                if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(tEquipment.getEquipType())) {
                    String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[0].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(tEquipment.getEquipType())) {
                    String[] command = equipDict.get(OfficeEnum.EquipType.LIGHT.getCode()).split(",")[0].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.AIR_CONDITION.getCode().equalsIgnoreCase(tEquipment.getEquipType())){
                    String[] command = equipDict.get(OfficeEnum.EquipType.AIR_CONDITION.getCode()).split(",")[0].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.MACHINE.getCode().equalsIgnoreCase(tEquipment.getEquipType())){
                    String[] command = equipDict.get(OfficeEnum.EquipType.MACHINE.getCode()).split(",")[0].split(":");
                    msg.put(command[0], command[1]);
                }
                else
                    throw new ServiceException("未知类型设备");

                sendClient.publish(qry.getEquipControl(), JSONObject.toJSONString(msg));
                tEquipment.setRecentOpenTime(new Date());
            } else if ("N".equalsIgnoreCase(tEquipment.getOnOff())) {
                Map<String, String> msg = new HashMap<>();
                if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(tEquipment.getEquipType())) {
                    String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(tEquipment.getEquipType())) {
                    String[] command = equipDict.get(OfficeEnum.EquipType.LIGHT.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.AIR_CONDITION.getCode().equalsIgnoreCase(tEquipment.getEquipType())){
                    String[] command = equipDict.get(OfficeEnum.EquipType.AIR_CONDITION.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);
                }
                if (OfficeEnum.EquipType.MACHINE.getCode().equalsIgnoreCase(tEquipment.getEquipType())){
                    String[] command = equipDict.get(OfficeEnum.EquipType.MACHINE.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);
                } else
                    throw new ServiceException("未知类型设备");

                sendClient.publish(qry.getEquipControl(), JSONObject.toJSONString(msg));
            }
        } catch (Exception e) {
            throw new ServiceException("操作失败");
        }
        // messageArrived 里面处理 消息发送到接收端时触发；
        tEquipment.setUpdateBy(SecurityUtils.getUserId() + "");

        return toAjax(tEquipmentService.updateTEquipment(tEquipment));
    }

    @PostConstruct
    /**
     * 扫描所有设备返回
     */
    public void scanEquipStatus() {
        MqttAcceptClient subClient = new MqttAcceptClient();
        List<TEquipment> equipments = tEquipmentService.selectTEquipmentList(new TEquipment());
//        for (TEquipment equipment : equipments) {
//            if (StringUtils.isNotEmpty(equipment.getEquipControl()))
//                subClient.subscribe(equipment.getEquipControl(), 0);
//        }
    }

    /**
     * 查询设备列表列表
     */
    @PreAuthorize("@ss.hasPermi('office:equipment:list')")
    @GetMapping("/listAvailable")
    public TableDataInfo listAvailable(TEquipment tEquipment) {
        tEquipment.setCreateBy(SecurityUtils.getUserId() + "");
        List<TEquipment> list = tEquipmentService.selectAvailableEquipmentList(tEquipment);
        return getDataTable(list);
    }

}
