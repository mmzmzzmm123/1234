package com.ruoyi.office.mqtt;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.TEquipment;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.service.ITMqttMsgService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Component
public class MqttSendClient {


    /*  一、 最佳实践
       我们经常被问及如何选择正确的 QoS 级别的建议。以下是一些可以帮助您做出决策的指南。适合您的 QoS 在很大程度上取决于您的用例。
       当……时使用 QoS 0
       您在发送方和接收方之间建立了完全或大部分稳定的连接。QoS 0 的一个经典用例是通过有线连接将测试客户端或前端应用程序连接到 MQTT 代理。
       您不介意偶尔丢失一些消息。如果数据不是那么重要或数据发送间隔很短，一些消息的丢失是可以接受的
       您不需要消息队列。仅当断开连接的客户端具有 QoS 1 或 2 和持久会话时，消息才会排队。
       当……时使用 QoS 1
       您需要获取每条消息，并且您的用例可以处理重复消息。QoS 级别 1 是最常用的服务级别，因为它保证消息至少到达一次，但允许多次传递。当然，您的应用程序必须容忍重复并能够相应地处理它们。
       您无法承受 QoS 2 的开销font>。QoS 1 传递消息的速度比 QoS 2 快得多。
      当……时使用 QoS 2
       对您的应用程序来说，只接收一次所有消息至关重要。如果重复交付可能会损害应用程序用户或订阅客户端，则通常会出现这种情况。请注意开销，并且 QoS 2 交互需要更多时间才能完成。
        二、QoS 1 和 2 消息的排队
   使用 QoS 1 和 2 发送的所有消息都会排队等待离线客户端，直到客户端再次可用。然而，只有当客户端有一个持久会话时，这种排队才是可能的。
        三、 当客户端连接到borker时，可以请求持续性会话。客户端使用cleanSession标志告诉broker，请求的哪种会话。
   标志设置为True是，客户端不需要持久性连接。不管何种原因，当它断开连接时，当前的会话信息和所有的消息将会丢失。
   当cleanSession标志设置为false时，broker会为客户端创建一个持续性连接，所有的会话信息和消息将会被保留，直到下一次客户端将cleanSession标志设置为true。如果客户端将cleanSession标志设置为false且broker已经有了客户端的连接会话，客户端连接时broker会使用已有的会话并将之前排队的信息传递给client。
          */
    private final int defaultQos = 1;

    private static MqttClient mqttClient;
    private MqttSendCallBack mqttSendCallBack;
    private String host;
    private String username;
    private String password;
    private String clientId;
    private int timeout;
    private int keepalive;

    public MqttSendClient(MqttProperties properties, MqttSendCallBack callBack) {//String host, String username, String password, String clientId, int timeOut, int keepAlive) {
        this.host = properties.getHostUrl();
        this.username = properties.getUsername();
        this.password = properties.getPassword();
        this.clientId = properties.getClientId();
        this.timeout = properties.getTimeout();
        this.keepalive = properties.getKeepAlive();
        this.mqttSendCallBack = callBack;
    }

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
        try {
            if (mqttClient == null) {
                mqttClient = new MqttClient(host, clientId, new MemoryPersistence());
                mqttClient.setCallback(mqttSendCallBack);
            }

            //String uuid = UUID.randomUUID().toString().replaceAll("-",""); //设置每一个客户端的id
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(timeout);
            options.setKeepAliveInterval(keepalive);
            options.setCleanSession(true);
            // 自动重连
            options.setAutomaticReconnect(true);
            if (!mqttClient.isConnected()) {
                mqttClient.connect(options);
            } else {
                mqttClient.disconnect();
                mqttClient.connect(options);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
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
            throw new ServiceException("主题不存在：" + mTopic);
        }

        MqttDeliveryToken token;//Delivery:配送
        synchronized (this) {//注意：这里一定要同步，否则，在多线程publish的情况下，线程会发生死锁，分析见文章最后补充
            try {
//                sub(topic);
                token = mTopic.publish(message);//也是发送到执行队列中，等待执行线程执行，将消息发送到消息中间件
                token.waitForCompletion(1000L);
            } catch (MqttPersistenceException e) {
                log.error(e.getMessage());
            } catch (MqttException mqttException) {
                if (mqttException.getMessage().equalsIgnoreCase("Client is not connected")) {
                    connect();
                } else {
                    log.error(mqttException.getMessage());
                }
            } catch (Exception e) {
                log.error(topic + " mqtt发送消息异常 " + pushMessage);
                throw new ServiceException(e.getMessage());
            }
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
