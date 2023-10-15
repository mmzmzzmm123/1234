package com.ruoyi.office.task;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.CloudHornRegResponse;
import com.ruoyi.office.horn.HornConfig;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.service.ITEquipmentService;
import com.ruoyi.office.service.ITRoomOrderService;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 定时任务调度测试
 *
 * @author ruoyi
 */
@Component("officeTask")
public class OfficeTask {

    @Autowired
    ITRoomOrderService roomOrderService;

    @Autowired
    ITRoomService roomService;

    @Autowired
    ITEquipmentService equipmentService;

    @Autowired
    ISysDictDataService dictDataService;

    /**
     * 扫描快到期的订单,进行语音提醒到期时间
     */
    public void msgOrder() {
        roomOrderService.msgOrder();
    }

    /**
     * 扫描快到期的订单,进行语音提醒到期时间
     */
    public void scanOrder() {
        roomOrderService.scanOrder();
    }


    /**
     * 扫描最近打开设备，如果是门则进行关闭操作
     * 5-10秒
     */
    public void scanOpenEquipment() {
        TEquipment equipment = new TEquipment();
        equipment.setOnOff("Y");
        final List<TEquipment> equipments = equipmentService.selectTEquipmentList(equipment);

        if (equipments.size() == 0)
            return;

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type");
        Map<String, String> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getRemark));

        SysDictData clooseDoorQry = new SysDictData();
        clooseDoorQry.setDictLabel("close_door");
        final List<SysDictData> clooseDoor = dictDataService.selectDictDataList(clooseDoorQry);
        int clooseDoorMinute = 15; // 秒
        if (clooseDoor.size() > 0) {
            clooseDoorMinute = Integer.parseInt(clooseDoor.get(0).getDictValue());
        }

        MqttSendClient sendClient = new MqttSendClient();
        for (TEquipment eq : equipments) {
            if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(eq.getEquipType())) {
                if (eq.getRecentOpenTime() != null && (new Date().getTime() - eq.getRecentOpenTime().getTime()) > (clooseDoorMinute * 1000)) {
                    Map<String, String> msg = new HashMap<>();
                    String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);

                    try {
                        sendClient.publish(eq.getEquipControl(), JSONObject.toJSONString(msg));
                    } catch (Exception e) {
                        continue;
                    }
                    TEquipment up = new TEquipment();
                    up.setId(eq.getId());
                    up.setOnOff("N");
                    equipmentService.updateTEquipment(up);
                }
            }

        }
    }


    /**
     * 扫描未支付订单进行取消操作
     */
    public void scanToPayOrder() {
        roomOrderService.scanToPayOrder();
    }

    /**
     * 扫描超时订单进行取消操作
     */
   /* public void scanOvertimeOrder(){
        roomOrderService.scanOvertimeOrder();
    }*/

}
