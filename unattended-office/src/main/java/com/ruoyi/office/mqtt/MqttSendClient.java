package com.ruoyi.office.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.enums.OfficeEnum;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class MqttSendClient {

    private final int defaultQos = 2;

    @Autowired
    private MqttSendCallBack mqttSendCallBack;

    @Autowired
    private MqttProperties mqttProperties;

    private static MqttClient mqttClient;

    private static MqttClient getClient() {

        return mqttClient;
    }

    private static void setClient(MqttClient client) {

        MqttSendClient.mqttClient = client;
    }

    /**
     * 客户端连接
     *
     * @return
     */
    public void connect() {
        MqttClient client = null;

        try {

            //String uuid = UUID.randomUUID().toString().replaceAll("-",""); //设置每一个客户端的id
            client = new MqttClient(mqttProperties.getHostUrl(), mqttProperties.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttProperties.getUsername());
            options.setPassword(mqttProperties.getPassword().toCharArray());
            options.setConnectionTimeout(mqttProperties.getTimeout());
            options.setKeepAliveInterval(mqttProperties.getKeepAlive());
            options.setCleanSession(true);
            // 自动重连
            options.setAutomaticReconnect(true);

            MqttSendClient.setClient(client);
            try {
                // 设置回调
                client.setCallback(mqttSendCallBack);
                client.connect(options);
            } catch (Exception e) {

                e.printStackTrace();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param onOff 开： true 关：false
     */
    public void publish(TEquipment equipment, boolean onOff, String pushMessage) {
        if (StringUtils.isNotEmpty(equipment.getOnOffMsg())) {
            String[] msgs = equipment.getOnOffMsg().split(",");
            if (msgs.length != 2) {
                log.error("设备开关代码配置错误:" + equipment.toString());
                throw new ServiceException("设备开关代码配置错误");
            }
            Map<String, String> msg = new HashMap<>();
            if (onOff) {
                String[] command = msgs[0].split(":");
                msg.put(command[0], command[1]);
                publish(equipment.getEquipControl(), JSONObject.toJSONString(msg));
            } else {
                String[] command = msgs[1].split(":");
                msg.put(command[0], command[1]);
                publish(equipment.getEquipControl(), JSONObject.toJSONString(msg));
            }
        } else {
            publish(defaultQos, false, equipment.getEquipControl(), pushMessage);
        }
    }

    /**
     * 发布，默认qos为0，非持久化
     *
     * @param topic       主题名
     * @param pushMessage 消息
     */
    public void publish(String topic, String pushMessage) {
        publish(defaultQos, false, topic, pushMessage);
    }

    /**
     * 发布
     *
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos, boolean retained, String topic, String pushMessage) {
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.getBytes());
        MqttTopic mTopic = MqttSendClient.getClient().getTopic(topic);
        if (null == mTopic) {
            log.error("主题不存在:{}", mTopic);
        }
        try {
            mTopic.publish(message);
            log.info(topic + "消息发送成功" + pushMessage);
        } catch (MqttException mqttException) {
            if (mqttException.getMessage().equalsIgnoreCase("Client is not connected")) {
                connect();
            }
        } catch (Exception e) {
            log.error("mqtt发送消息异常:", e);
            log.error(topic + " mqtt发送消息异常 " + pushMessage);
            throw new ServiceException(e.getMessage());
        }
    }

    public void sub(String topic) {
        sub(topic, defaultQos);
    }

    public void sub(String topic, int qos) {
        try {
            MqttSendClient.getClient().subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
