package com.ruoyi.office.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.mqtt.MqttSendClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TMqttMsgMapper;
import com.ruoyi.office.domain.TMqttMsg;
import com.ruoyi.office.service.ITMqttMsgService;

/**
 * mqtt消息发送接收Service业务层处理
 *
 * @author ruoyi
 * @date 2023-11-20
 */
@Service
public class TMqttMsgServiceImpl extends ServiceImpl<TMqttMsgMapper, TMqttMsg> implements ITMqttMsgService {
    @Autowired
    private TMqttMsgMapper tMqttMsgMapper;

    /**
     * 查询mqtt消息发送接收
     *
     * @param id mqtt消息发送接收主键
     * @return mqtt消息发送接收
     */
    @Override
    public TMqttMsg selectTMqttMsgById(Long id) {
        return tMqttMsgMapper.selectTMqttMsgById(id);
    }

    /**
     * 查询mqtt消息发送接收列表
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return mqtt消息发送接收
     */
    @Override
    public List<TMqttMsg> selectTMqttMsgList(TMqttMsg tMqttMsg) {
        return tMqttMsgMapper.selectTMqttMsgList(tMqttMsg);
    }

    /**
     * 新增mqtt消息发送接收
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return 结果
     */
    @Override
    public int insertTMqttMsg(TMqttMsg tMqttMsg) {
        tMqttMsg.setCreateTime(DateUtils.getNowDate());
        return tMqttMsgMapper.insertTMqttMsg(tMqttMsg);
    }

    /**
     * 修改mqtt消息发送接收
     *
     * @param tMqttMsg mqtt消息发送接收
     * @return 结果
     */
    @Override
    public int updateTMqttMsg(TMqttMsg tMqttMsg) {
        tMqttMsg.setUpdateTime(DateUtils.getNowDate());
        return tMqttMsgMapper.updateTMqttMsg(tMqttMsg);
    }

    /**
     * 批量删除mqtt消息发送接收
     *
     * @param ids 需要删除的mqtt消息发送接收主键
     * @return 结果
     */
    @Override
    public int deleteTMqttMsgByIds(Long[] ids) {
        return tMqttMsgMapper.deleteTMqttMsgByIds(ids);
    }

    /**
     * 删除mqtt消息发送接收信息
     *
     * @param id mqtt消息发送接收主键
     * @return 结果
     */
    @Override
    public int deleteTMqttMsgById(Long id) {
        return tMqttMsgMapper.deleteTMqttMsgById(id);
    }

    @Autowired
    MqttSendClient sendClient;

    @Override
    public void publish(TEquipment qry, boolean onOff, String msg) {
//        MqttSendClient sendClient = new MqttSendClient();
        try {
            sendClient.publish(qry, onOff, msg);
            logAfterSend(qry, msg);
        } catch (Exception e) {
            logWhenError(qry, msg, e.getMessage());
        }
    }


    private void logAfterSend(TEquipment equipment, String msg) {
        try {
            TMqttMsg mqttMsg = new TMqttMsg();
            mqttMsg.setClientId(equipment.getEquipControl());
            mqttMsg.setTopic(equipment.getEquipControl());
            if (StringUtils.isNotEmpty(equipment.getOnOffMsg())) {
                mqttMsg.setMessage(equipment.getOnOffMsg());
            } else {
                mqttMsg.setMessage(msg);
            }
            mqttMsg.setCreateTime(new Date());
            mqttMsg.setMsgType("SEND");
            insertTMqttMsg(mqttMsg);
        } catch (Exception e) {
            log.error("mqtt发送前记录异常:", e);
            throw new ServiceException(e.getMessage());
        }
    }

    private void logWhenError(TEquipment equipment, String msg, String errMsg) {
        try {
            TMqttMsg mqttMsg = new TMqttMsg();
            mqttMsg.setClientId(equipment.getEquipControl());
            mqttMsg.setTopic(equipment.getEquipControl());
//            mqttMsg.setQos(qos + "");
            if (StringUtils.isNotEmpty(equipment.getOnOffMsg())) {
                mqttMsg.setMessage(equipment.getOnOffMsg());
            } else {
                mqttMsg.setMessage(msg);
            }
            mqttMsg.setCreateTime(new Date());
            mqttMsg.setMsgType("SEND_ERROR");
            mqttMsg.setRemark(errMsg);
            insertTMqttMsg(mqttMsg);
        } catch (Exception e) {
            log.error("mqtt发送前记录异常:", e);
        }
    }

}
