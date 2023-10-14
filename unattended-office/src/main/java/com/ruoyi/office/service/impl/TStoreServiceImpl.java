package com.ruoyi.office.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.WxStoreListQryVo;
import com.ruoyi.office.domain.vo.WxStoreListRspVo;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.service.*;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStoreMapper;

/**
 * 商家用户店铺Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TStoreServiceImpl extends ServiceImpl<TStoreMapper, TStore> implements ITStoreService {
    @Autowired
    private TStoreMapper tStoreMapper;

    /**
     * 查询商家用户店铺
     *
     * @param id 商家用户店铺主键
     * @return 商家用户店铺
     */
    @Override
    public TStore selectTStoreById(Long id) {
        return tStoreMapper.selectTStoreById(id);
    }

    /**
     * 查询商家用户店铺列表
     *
     * @param tStore 商家用户店铺
     * @return 商家用户店铺
     */
    @Override
    public List<TStore> selectTStoreList(TStore tStore) {
        return tStoreMapper.selectTStoreList(tStore);
    }

    /**
     * 新增商家用户店铺
     *
     * @param tStore 商家用户店铺
     * @return 结果
     */
    @Override
    public int insertTStore(TStore tStore) {
        tStore.setCreateTime(DateUtils.getNowDate());
        tStore.setUserId(Long.parseLong(tStore.getCreateBy()));
        return tStoreMapper.insertTStore(tStore);
    }

    /**
     * 修改商家用户店铺
     *
     * @param tStore 商家用户店铺
     * @return 结果
     */
    @Override
    public int updateTStore(TStore tStore) {
        tStore.setUpdateTime(DateUtils.getNowDate());
        return tStoreMapper.updateTStore(tStore);
    }

    /**
     * 批量删除商家用户店铺
     *
     * @param ids 需要删除的商家用户店铺主键
     * @return 结果
     */
    @Override
    public int deleteTStoreByIds(Long[] ids) {
        return tStoreMapper.deleteTStoreByIds(ids);
    }

    /**
     * 删除商家用户店铺信息
     *
     * @param id 商家用户店铺主键
     * @return 结果
     */
    @Override
    public int deleteTStoreById(Long id) {
        return tStoreMapper.deleteTStoreById(id);
    }

    @Autowired
    ITStorePackageService storePackageService;
    @Autowired
    ITWxUserAmountService wxUserAmountService;

    @Override
    public List<WxStoreListRspVo> selectWxStoreList(WxStoreListQryVo wxStoreListQryVo, Long userId) {
        List<WxStoreListRspVo> resList = tStoreMapper.selectWxStoreList(wxStoreListQryVo);
        for (WxStoreListRspVo vo : resList) {
            TStorePackage storePackage = new TStorePackage();
//            storePackage.setCreateBy(vo.getCreateBy());
            storePackage.setStoreId(vo.getId());
            final List<TStorePackage> tStorePackages = storePackageService.selectTStorePackageList(storePackage);
            vo.setStorePackageList(tStorePackages);

            TWxUserAmount wxUserAmount = new TWxUserAmount();
            wxUserAmount.setWxUserId(userId);
            wxUserAmount.setUserId(Long.parseLong(vo.getCreateBy()));
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() > 0) {
                vo.setBalance(wxUserAmounts.get(0).getAmount().toPlainString());
            } else {
                vo.setBalance("0");
            }
        }
        return resList;
    }

    @Autowired
    ITEquipmentService equipmentService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ITRoomOrderService orderService;

    @Autowired
    private ITRoomService roomService;

    @Override
    public void openStore(Long id) {

        TRoomOrder order = orderService.selectTRoomOrderById(id);
        final TRoom room = roomService.selectTRoomById(order.getRoomId());

        TStore store = tStoreMapper.selectTStoreById(room.getStoreId());
        TEquipment equipment = equipmentService.selectTEquipmentById(store.getEquipId());

        if (equipment == null) {
            throw new ServiceException("未找到门店大门设备,请联系管理员");
        }

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type");
        Map<String, String> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getRemark));

        Map<String, String> msg = new HashMap<>();
        if (equipDict.containsKey(OfficeEnum.EquipType.DOOR.getCode())) {
            String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[0].split(":");
            msg.put(command[0], command[1]);
        } else {
            throw new ServiceException("未设置门禁开门代码");
        }

        MqttSendClient sendClient = new MqttSendClient();
        sendClient.publish(equipment.getEquipControl(), JSONObject.toJSONString(msg));

        equipment.setRecentOpenTime(new Date());
        equipment.setOnOff("Y");
        equipmentService.updateTEquipment(equipment);
    }

    @Override
    public void openStoreByStoreId(Long id) {
        TStore store = tStoreMapper.selectTStoreById(id);
        TEquipment equipment = equipmentService.selectTEquipmentById(store.getEquipId());

        SysDictData dictData = new SysDictData();
        dictData.setDictType("equipment_type");
        Map<String, String> equipDict = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getRemark));

        Map<String, String> msg = new HashMap<>();
        if (!OfficeEnum.EquipType.DOOR.getCode().equalsIgnoreCase(equipment.getEquipType())) {
            throw new ServiceException("该门店未设置门禁,请联系管理员");
        }
        if (equipDict.containsKey(OfficeEnum.EquipType.DOOR.getCode())) {
            String[] command = equipDict.get(OfficeEnum.EquipType.DOOR.getCode()).split(",")[0].split(":");
            msg.put(command[0], command[1]);
        } else {
            throw new ServiceException("未找到门禁设备代码,请联系管理员");
        }

        MqttSendClient sendClient = new MqttSendClient();
        sendClient.publish(equipment.getEquipControl(), JSONObject.toJSONString(msg));

        equipment.setRecentOpenTime(new Date());
        equipment.setOnOff("Y");
        equipmentService.updateTEquipment(equipment);
    }
}
