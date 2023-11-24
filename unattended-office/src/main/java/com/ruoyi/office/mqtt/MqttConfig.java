package com.ruoyi.office.mqtt;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {

    private static final Logger log = LoggerFactory.getLogger(MqttConfig.class);

    @Autowired
    private MqttProperties mqttProperties;

    @Autowired
    private MqttSendCallBack mqttSendCallBack;

    @Conditional(MqttCondition.class)
    @Bean
    public MqttSendClient getMqttSendClient() {
        MqttSendClient mqttSendClient = new MqttSendClient(mqttProperties, mqttSendCallBack);
        mqttSendClient.connect();

        return mqttSendClient;
    }

//    @Autowired
//    private MqttAcceptClient mqttAcceptClient;

   /* @Conditional(MqttCondition.class)
    @Bean*/
  /*  public MqttAcceptClient getMqttAcceptClient() {
        mqttAcceptClient.connect();
        //mqttAcceptClient.subscribe("test_queue",0);
        return mqttAcceptClient;
    }*/

}
