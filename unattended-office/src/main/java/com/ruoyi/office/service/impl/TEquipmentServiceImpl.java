package com.ruoyi.office.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.CloudHornRegResponse;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.office.service.ITStoreService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TEquipmentMapper;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.service.ITEquipmentService;

/**
 * 设备列表Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TEquipmentServiceImpl extends ServiceImpl<TEquipmentMapper, TEquipment> implements ITEquipmentService {
    @Autowired
    private TEquipmentMapper tEquipmentMapper;

    /**
     * 查询设备列表
     *
     * @param id 设备列表主键
     * @return 设备列表
     */
    @Override
    public TEquipment selectTEquipmentById(Long id) {
        return tEquipmentMapper.selectTEquipmentById(id);
    }

    /**
     * 查询设备列表列表
     *
     * @param tEquipment 设备列表
     * @return 设备列表
     */
    @Override
    public List<TEquipment> selectTEquipmentList(TEquipment tEquipment) {
        return tEquipmentMapper.selectTEquipmentList(tEquipment);
    }

    @Autowired
    ISysDictDataService dictDataService;

    /**
     * 新增设备列表
     *
     * @param tEquipment 设备列表
     * @return 结果
     */
    @Override
    public int insertTEquipment(TEquipment tEquipment) {
        tEquipment.setCreateTime(DateUtils.getNowDate());
        if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(tEquipment.getEquipType())) {
            // 发送 喇叭 注册 并记录注册结果
            SysDictData dictData = new SysDictData();
            dictData.setDictType("horn");
            final Map<String, String> hornConfig = dictDataService.selectDictDataList(dictData).stream().collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));
            Map<String, String> param = new HashMap<>();
            param.put("app_id", hornConfig.get("app_id"));
            param.put("app_secret", hornConfig.get("app_secret"));
            param.put("device_sn", tEquipment.getEquipControl());
            String response = HttpUtils.sendPost(hornConfig.get("url")+"/register", "");
            CloudHornRegResponse resp = JSONObject.parseObject(response, CloudHornRegResponse.class);

            tEquipment.setRemark(resp.getMsg()); // 设备注册返回
        }
        return tEquipmentMapper.insertTEquipment(tEquipment);
    }

    /**
     * 修改设备列表
     *
     * @param tEquipment 设备列表
     * @return 结果
     */
    @Override
    public int updateTEquipment(TEquipment tEquipment) {
        tEquipment.setUpdateTime(DateUtils.getNowDate());
        return tEquipmentMapper.updateTEquipment(tEquipment);
    }

    /**
     * 批量删除设备列表
     *
     * @param ids 需要删除的设备列表主键
     * @return 结果
     */
    @Override
    public int deleteTEquipmentByIds(Long[] ids) {
        return tEquipmentMapper.deleteTEquipmentByIds(ids);
    }

    /**
     * 删除设备列表信息
     *
     * @param id 设备列表主键
     * @return 结果
     */
    @Override
    public int deleteTEquipmentById(Long id) {
        return tEquipmentMapper.deleteTEquipmentById(id);
    }

    @Autowired
    ITStoreService storeService;
    @Autowired
    ITRoomService roomService;

    @Override
    public List<TEquipment> selectAvailableEquipmentList(TEquipment tEquipment) {

        List<TEquipment> equipments = tEquipmentMapper.selectTEquipmentList(tEquipment);

        TStore store = new TStore();
        store.setCreateBy(tEquipment.getCreateBy());
        final List<TStore> tStores = storeService.selectTStoreList(store);

        TRoom room = new TRoom();
        room.setCreateBy(tEquipment.getCreateBy());
        final List<TRoom> tRooms = roomService.selectTRoomList(room);


        return equipments;
    }
}
