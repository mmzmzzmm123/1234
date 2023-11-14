package com.ruoyi.office.service.impl;

import java.util.Date;
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
import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.horn.HornConfig;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.service.ITEquipmentService;
import com.ruoyi.office.service.ITStoreService;
import com.ruoyi.office.ttlock.TtlockConfig;
import com.ruoyi.office.util.WxMsgSender;
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
    public String openRoom(Long id) {
        TRoom room = tRoomMapper.selectTRoomById(id);
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        StringBuilder errMsg = new StringBuilder();
        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                throw new ServiceException("设备不存在" );
            }
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {

                CloudHornRegResponse resp = HornConfig.hornSend(currentEq.getEquipControl(), "已开门，欢迎使用十三将无人自助棋牌" );
                if (!"0".equalsIgnoreCase(resp.getCode())) {
                    errMsg.append("云喇叭消息发送失败" ).append(resp.getMsg()).append(";" );
                }
                TEquipment up = new TEquipment();
                up.setId(currentEq.getId());
                up.setRemark(resp.getData()); // 设备发送信息返回
                equipmentService.updateTEquipment(up);
            } else if (OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                try {
                    TStore store = storeService.selectTStoreById(room.getStoreId());
                    String username = store.getTtlockUname();
                    String password = store.getTtlockPwd();
                    TtlockTokenRes ttlockTokenRes = TtlockConfig.getToken(username, password);
                    String lockId = currentEq.getEquipControl();
                    TtlockGatewayRes res = TtlockConfig.unlock(ttlockTokenRes.getAccess_token(), lockId);
                } catch (Exception e) {
                    errMsg.append(e.getMessage());
                    continue;
                }
            } else {
                Map<String, String> msg = new HashMap<>();

                if (equipDict.containsKey(currentEq.getEquipType())) {
                    String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[0].split(":" );
                    msg.put(command[0], command[1]);
                } else {
                    errMsg.append(currentEq.getEquipType() + "类型的设备未设置开关代码;" );
                    continue;
                }
                try {
//                    sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                    sendClient.publish(currentEq, true, JSONObject.toJSONString(msg));

                    currentEq.setRecentOpenTime(new Date());
                    currentEq.setOnOff("Y" );
                    equipmentService.updateTEquipment(currentEq);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    continue;
                }

            }
        }

        return errMsg.toString();
    }

    @Override
    public void closeRoom(Long id) {

        TRoom room = tRoomMapper.selectTRoomById(id);
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        StringBuilder sb = new StringBuilder();
        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                  /*  SysDictData dictDataQry = new SysDictData();
                    dictDataQry.setDictType("horn");
                    final Map<String, String> hornConfig = dictDataService.selectDictDataList(dictDataQry).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));*/

               /* HornSendMsg hornMsg = new HornSendMsg();
                  hornMsg.setAppId(hornConfig.get("app_id"));
                    hornMsg.setAppSecret(hornConfig.get("app_secret"));
                hornMsg.setAppId(hornConfig.getAppId());
                hornMsg.setAppSecret(hornConfig.getAppSecret());
                hornMsg.setDeviceSn(currentEq.getEquipControl());
                hornMsg.setType(1);

                HornSendMsgData hornData = new HornSendMsgData();
                hornData.setCmdType("play");

                HornSendMsgDataInfo hornDataInfo = new HornSendMsgDataInfo();
                hornDataInfo.setInner(10);
                hornDataInfo.setTts("已开门，欢迎使用雀行无人麻将机");

                hornData.setInfo(hornDataInfo);
                hornMsg.setData(hornData);

//                    String response = HttpUtils.sendPost(hornConfig.get("url") + "/send", JSONObject.toJSONString(hornMsg));
                String response = HttpUtils.sendPost(hornConfig.getUrl() + "/send", JSONObject.toJSONString(hornMsg));
                CloudHornRegResponse resp = JSONObject.parseObject(response, CloudHornRegResponse.class);

                TEquipment up = new TEquipment();
                up.setId(currentEq.getId());
                up.setRemark(resp.getData()); // 设备发送信息返回
                equipmentService.updateTEquipment(up);*/
            } else {
                if (OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                    try {
                        TStore store = storeService.selectTStoreById(room.getStoreId());
                        String username = store.getTtlockUname();
                        String password = store.getTtlockPwd();
                        TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                        String lockId = currentEq.getEquipControl();
                        TtlockGatewayRes res = TtlockConfig.lock(ttlockTokenRes.getAccess_token(), lockId);
                    } catch (Exception e) {
                        log.error("订单结束 通通锁 ttlock 关闭失败" + e.getMessage());
                    }
                } else {
                    Map<String, String> msg = new HashMap<>();

                    if (equipDict.containsKey(currentEq.getEquipType())) {
                        String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[1].split(":" );
                        msg.put(command[0], command[1]);
                    } else {
//                    throw new ServiceException(currentEq.getEquipType() + "类型的设备未设置");
                        log.error(currentEq.getEquipType() + "类型的设备未设置" );
                        continue;
                    }

                    try {
//                        sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                        sendClient.publish(currentEq, false, JSONObject.toJSONString(msg));
                    } catch (Exception e) {
                        continue;
                    }
                    currentEq.setUpdateTime(new Date());
                    currentEq.setOnOff("N" );
                    equipmentService.updateTEquipment(currentEq);
//                break;
                }
            }
        }
    }

    @Override
    public void openRoomEquipment(RoomEquipeOpenReq req, Long userId) {
        TRoom room = tRoomMapper.selectTRoomById(req.getRoomId());
        if (Long.parseLong(room.getCreateBy()) != userId) {
            throw new ServiceException("非本人门店房间" );
        }
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        String hornSn = "";
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                hornSn = currentEq.getEquipControl();
            }
        }

        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                throw new ServiceException("未知的设备绑定" );
            }
            if (req.getEquipType().contains(OfficeEnum.EquipType.DOOR.getCode()) && OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                try {
                    TStore store = storeService.selectTStoreById(room.getStoreId());
                    String username = store.getTtlockUname();
                    String password = store.getTtlockPwd();
                    TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                    String lockId = currentEq.getEquipControl();
                    TtlockGatewayRes res = TtlockConfig.unlock(ttlockTokenRes.getAccess_token(), lockId);

                    currentEq.setRecentOpenTime(new Date());
                    currentEq.setOnOff("Y" );
                    equipmentService.updateTEquipment(currentEq);

                    CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "门已打开" );
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }
            } else {
                if (req.getEquipType().contains((currentEq.getEquipType()))) {

                    Map<String, String> msg = new HashMap<>();

                    if (equipDict.containsKey(currentEq.getEquipType())) {
                        String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[0].split(":" );
                        msg.put(command[0], command[1]);
                    } else {
                        throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                    }

                    try {
//                        sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                        sendClient.publish(currentEq, true, JSONObject.toJSONString(msg));

                        currentEq.setRecentOpenTime(new Date());
                        currentEq.setOnOff("Y" );
                        equipmentService.updateTEquipment(currentEq);

                        if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(req.getEquipType())) {
//                    hornMsg(hornSn, "门已打开");
                            CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "门已打开" );
                        } else if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
//                    hornMsg(hornSn, "房间电源已开启");
                            CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "房间电源已开启" );
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }

    }

    @Override
    public void closeRoomEquipment(RoomEquipeOpenReq req, Long userId) {
        TRoom room = tRoomMapper.selectTRoomById(req.getRoomId());
        if (Long.parseLong(room.getCreateBy()) != userId) {
            throw new ServiceException("非本人门店房间" );
        }
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();

        String hornSn = "";
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                hornSn = currentEq.getEquipControl();
            }
        }

        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                throw new ServiceException("未知的设备绑定" );
            }

            if (req.getEquipType().contains(OfficeEnum.EquipType.DOOR.getCode()) && OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                try {
                    TStore store = storeService.selectTStoreById(room.getStoreId());
                    String username = store.getTtlockUname();
                    String password = store.getTtlockPwd();
                    TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                    String lockId = currentEq.getEquipControl();
                    TtlockGatewayRes res = TtlockConfig.lock(ttlockTokenRes.getAccess_token(), lockId);

                    currentEq.setRecentOpenTime(new Date());
                    currentEq.setOnOff("N" );
                    equipmentService.updateTEquipment(currentEq);

                    CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "门已关闭" );
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }
            } else {
                if (req.getEquipType().contains((currentEq.getEquipType()))) {

                    Map<String, String> msg = new HashMap<>();

                    if (equipDict.containsKey(currentEq.getEquipType())) {
                        String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[1].split(":" );
                        msg.put(command[0], command[1]);
                    } else {
                        throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                    }

                    try {
//                        sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                        sendClient.publish(currentEq, false, JSONObject.toJSONString(msg));
                    } catch (Exception e) {
                        continue;
                    }

                    currentEq.setRecentOpenTime(new Date());
                    currentEq.setOnOff("N" );
                    equipmentService.updateTEquipment(currentEq);

                    if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(req.getEquipType())) {
//                    hornMsg(hornSn, "门已关闭");
                        CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "门已关闭" );
                    } else if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
//                    hornMsg(hornSn, "房间电源已关闭");
                        CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "房间电源已关闭" );
                    }
                }
            }
        }
    }

    /**
     * 保洁只开包厢门和电源
     *
     * @param req
     * @param userId
     */
    @Override
    public void openCleanerRoomEquipment(CleanerRoomOpenReq req, Long userId) {
        TRoom room = tRoomMapper.selectTRoomById(req.getRoomId());
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        String hornSn = "";
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                hornSn = currentEq.getEquipControl();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                Map<String, String> msg = new HashMap<>();

                if (equipDict.containsKey(currentEq.getEquipType())) {
                    String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[0].split(":" );
                    msg.put(command[0], command[1]);
                } else {
                    sb.append("未知的设备类型" + currentEq.getEquipType());
                    continue;
//                    throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                }

                try {
//                    sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                    sendClient.publish(currentEq, true, JSONObject.toJSONString(msg));

                    CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "请开始保洁" );
                } catch (Exception e) {
                    continue;
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("Y" );
                equipmentService.updateTEquipment(currentEq);

            } else if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                Map<String, String> msg = new HashMap<>();

                if (equipDict.containsKey(currentEq.getEquipType())) {
                    String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[0].split(":" );
                    msg.put(command[0], command[1]);
                } else {
                    sb.append("未知的设备类型" + currentEq.getEquipType());
                    continue;
//                    throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                }

                try {
//                    sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                    sendClient.publish(currentEq, true, JSONObject.toJSONString(msg));
                } catch (Exception e) {
                    continue;
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("Y" );
                equipmentService.updateTEquipment(currentEq);
            } else if (OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                try {
                    TStore store = storeService.selectTStoreById(room.getStoreId());
                    String username = store.getTtlockUname();
                    String password = store.getTtlockPwd();
                    TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                    String lockId = currentEq.getEquipControl();
                    TtlockGatewayRes res = TtlockConfig.unlock(ttlockTokenRes.getAccess_token(), lockId);
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("Y" );
                equipmentService.updateTEquipment(currentEq);
                CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "请开始保洁" );
            }
        }

        if (sb.length() > 0) {
            throw new ServiceException(sb.toString());
        }
    }

    /**
     * 保洁只开包厢门和电源
     *
     * @param req
     * @param userId
     */
    @Override
    public void closeCleanerRoomEquipment(CleanerRoomOpenReq req, Long userId) {
        TRoom room = tRoomMapper.selectTRoomById(req.getRoomId());
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());

        final Map<Long, TEquipment> equipmentMap = equipments.stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type" );
        Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        String hornSn = "";
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                hornSn = currentEq.getEquipControl();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String equip : equips.split("," )) {
            TEquipment currentEq = equipmentMap.get(Long.parseLong(equip));
            if (currentEq == null) {
                continue;
            }
            if (OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                Map<String, String> msg = new HashMap<>();

                if (equipDict.containsKey(currentEq.getEquipType())) {
                    String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[1].split(":" );
                    msg.put(command[0], command[1]);
                } else {
                    sb.append("未知的设备类型" + currentEq.getEquipType());
                    continue;
//                    throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                }

                try {
//                    sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                    sendClient.publish(currentEq, false, JSONObject.toJSONString(msg));

                    CloudHornRegResponse resp = HornConfig.hornSend(hornSn, "辛苦您了" );
                } catch (Exception e) {
                    continue;
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("N" );
                equipmentService.updateTEquipment(currentEq);

            } else if (OfficeEnum.EquipType.LIGHT.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                Map<String, String> msg = new HashMap<>();

                if (equipDict.containsKey(currentEq.getEquipType())) {
                    String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[1].split(":" );
                    msg.put(command[0], command[1]);
                } else {
                    sb.append("未知的设备类型" + currentEq.getEquipType());
                    continue;
//                    throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
                }

                try {
//                    sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                    sendClient.publish(currentEq, false, JSONObject.toJSONString(msg));
                } catch (Exception e) {
                    continue;
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("N" );
                equipmentService.updateTEquipment(currentEq);
            } else if (OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {
                try {
                    TStore store = storeService.selectTStoreById(room.getStoreId());
                    String username = store.getTtlockUname();
                    String password = store.getTtlockPwd();
                    TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                    String lockId = currentEq.getEquipControl();
                    TtlockGatewayRes res = TtlockConfig.lock(ttlockTokenRes.getAccess_token(), lockId);
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("N" );
                equipmentService.updateTEquipment(currentEq);

            }
        }

        if (sb.length() > 0) {
            throw new ServiceException(sb.toString());
        }
    }

    @Autowired
    ITStoreService storeService;

    @Override
    public void openCleanerStore(CleanerRoomOpenReq req, Long userId) {
        TStore store = storeService.selectTStoreById(req.getStoreId());
        Long equipId = store.getEquipId();

        TEquipment currentEq = equipmentService.selectTEquipmentById(equipId);
        if (currentEq == null) {
            throw new ServiceException("未知的设备绑定" );
        }

        if (OfficeEnum.EquipType.TTLOCK.getCode().equalsIgnoreCase(currentEq.getEquipType())) {

            try {
                String username = store.getTtlockUname();
                String password = store.getTtlockPwd();
                TtlockTokenRes ttlockTokenRes = TtlockConfig.getTokenTest(username, password);
                String lockId = currentEq.getEquipControl();
                TtlockGatewayRes res = TtlockConfig.unlock(ttlockTokenRes.getAccess_token(), lockId);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }

            currentEq.setRecentOpenTime(new Date());
            currentEq.setOnOff("Y" );
            equipmentService.updateTEquipment(currentEq);
        } else {
            SysDictData dictData = new SysDictData();
            dictData.setDictType("equipment_type" );
            Map<String, SysDictData> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, Function.identity()));

            MqttSendClient sendClient = new MqttSendClient();
            Map<String, String> msg = new HashMap<>();
            if (equipDict.containsKey(currentEq.getEquipType())) {
                String[] command = equipDict.get(currentEq.getEquipType()).getRemark().split("," )[0].split(":" );
                msg.put(command[0], command[1]);
            } else {
                throw new ServiceException("未知的设备类型" + currentEq.getEquipType());
            }

            try {
//                sendClient.publish(currentEq.getEquipControl(), JSONObject.toJSONString(msg));
                sendClient.publish(currentEq, true, JSONObject.toJSONString(msg));

                currentEq.setRecentOpenTime(new Date());
                currentEq.setOnOff("Y" );
                equipmentService.updateTEquipment(currentEq);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage());
            }
        }
    }

}
