package com.ruoyi.office.task;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.CloudHornRegResponse;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.service.ITEquipmentService;
import com.ruoyi.office.service.ITRoomOrderService;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
     * 扫描快到期的订单
     */
    public void scanOrder() {
        Map<Long, TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment()).stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        // 发送 喇叭 注册 并记录注册结果
        SysDictData dictData = new SysDictData();
        dictData.setDictType(OfficeEnum.EquipType.HORN.getCode());
        final Map<String, String> hornConfig = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));
        Map<String, String> param = new HashMap<>();
        param.put("app_id", hornConfig.get("app_id"));
        param.put("app_secret", hornConfig.get("app_secret"));
        int minutes = Integer.parseInt(hornConfig.get("minute"));

        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setStatus(OfficeEnum.RoomOrderStatus.USING.getCode());
        List<TRoomOrder> roomOrderList = roomOrderService.selectTRoomOrderList(roomOrder);
        for (TRoomOrder order : roomOrderList) {
            // 默认只通知一次
            if ("Y".equalsIgnoreCase(order.getRemark())) {
                continue;
            }
            int diff = Math.abs((int) ((new Date().getTime() - order.getEndTime().getTime()) / (1000 * 60)));
            if (diff < minutes) {
                TRoom room = roomService.selectTRoomById(order.getRoomId());
                for (String s : room.getTableCode().split(",")) {
                    TEquipment equipment = equipments.get(Long.parseLong(s));
                    if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(equipment.getEquipType())) {
                        param.put("device_sn", equipment.getEquipControl());
                        String response = HttpUtils.sendPost(hornConfig.get("url") + "/send", "您的订单还有" + minutes + "分钟结束，请及时续费，以免断电影响使用，谢谢");
                        CloudHornRegResponse resp = JSONObject.parseObject(response, CloudHornRegResponse.class);
                        order.setRemark("Y");// 标识已经通知
                        roomOrderService.updateTRoomOrder(order);
                    }
                }
            }
        }
        System.out.println("订单结束提醒完成");
    }


    /**
     * 扫描最近打开设备，进行关闭操作
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

        MqttSendClient sendClient = new MqttSendClient();
        for (TEquipment eq : equipments) {
            if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(eq.getEquipType())) {
                if (DateUtils.differentDaysByMillisecond(eq.getRecentOpenTime(), new Date()) > 15 * 1000) {
                    Map<String, String> msg = new HashMap<>();
                    String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[1].split(":");
                    msg.put(command[0], command[1]);

                    sendClient.publish(eq.getEquipControl(), JSONObject.toJSONString(msg));

                    TEquipment up = new TEquipment();
                    up.setId(eq.getId());
                    up.setOnOff("N");
                    equipmentService.updateTEquipment(eq);
                }
            }

        }
    }


}
