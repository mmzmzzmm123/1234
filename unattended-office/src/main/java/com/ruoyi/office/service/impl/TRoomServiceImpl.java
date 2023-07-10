package com.ruoyi.office.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.service.ITEquipmentService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomMapper;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.service.ITRoomService;

/**
 * 店铺房间Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TRoomServiceImpl extends ServiceImpl<TRoomMapper, TRoom> implements ITRoomService {
    @Autowired
    private TRoomMapper tRoomMapper;

    /**
     * 查询店铺房间
     *
     * @param id 店铺房间主键
     * @return 店铺房间
     */
    @Override
    public TRoom selectTRoomById(Long id) {
        return tRoomMapper.selectTRoomById(id);
    }

    /**
     * 查询店铺房间列表
     *
     * @param tRoom 店铺房间
     * @return 店铺房间
     */
    @Override
    public List<TRoom> selectTRoomList(TRoom tRoom) {
        return tRoomMapper.selectTRoomList(tRoom);
    }

    /**
     * 新增店铺房间
     *
     * @param tRoom 店铺房间
     * @return 结果
     */
    @Override
    public int insertTRoom(TRoom tRoom) {
        tRoom.setCreateTime(DateUtils.getNowDate());
        return tRoomMapper.insertTRoom(tRoom);
    }

    /**
     * 修改店铺房间
     *
     * @param tRoom 店铺房间
     * @return 结果
     */
    @Override
    public int updateTRoom(TRoom tRoom) {
        tRoom.setUpdateTime(DateUtils.getNowDate());
        return tRoomMapper.updateTRoom(tRoom);
    }

    /**
     * 批量删除店铺房间
     *
     * @param ids 需要删除的店铺房间主键
     * @return 结果
     */
    @Override
    public int deleteTRoomByIds(Long[] ids) {
        return tRoomMapper.deleteTRoomByIds(ids);
    }

    /**
     * 删除店铺房间信息
     *
     * @param id 店铺房间主键
     * @return 结果
     */
    @Override
    public int deleteTRoomById(Long id) {
        return tRoomMapper.deleteTRoomById(id);
    }

    @Autowired
    ITEquipmentService equipmentService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public void openRoom(Long id) {
        TRoom room = tRoomMapper.selectTRoomById(id);
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type");
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));


        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        for (String equip : equips.split(",")) {
            for (TEquipment eq : equipments) {
                if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(eq.getEquipType()))
                    continue;

                if (eq.getId() == Long.parseLong(equip)) {
                    Map<String, String> msg = new HashMap<>();
                    msg.put("command", "a1");

                    if (equipDict.containsKey(eq.getEquipType())) {
                        String[] command = equipDict.get(eq.getEquipType()).getRemark().split(",")[0].split(":");
                        msg.put(command[0], command[1]);
                    } else {
                        throw new ServiceException(eq.getEquipType() + "类型的设备未设置");
                    }

                    sendClient.publish(eq.getEquipControl(), JSONObject.toJSONString(msg));
                    eq.setOnOff("Y");
                    equipmentService.updateTEquipment(eq);
                    break;
                }
            }
        }

    }
}
