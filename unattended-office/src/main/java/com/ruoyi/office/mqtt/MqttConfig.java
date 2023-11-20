package com.ruoyi.office.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MqttConfig {

    @Autowired
    private MqttSendClient mqttSendClient;

/*    @Conditional(MqttCondition.class)
    @Bean
    public MqttSendClient getMqttSendClient() {
        mqttSendClient.connect();
        return mqttSendClient;
    }*/

    @Autowired
    private MqttAcceptClient mqttAcceptClient;

   /* @Conditional(MqttCondition.class)
    @Bean*/
    public MqttAcceptClient getMqttAcceptClient() {
        mqttAcceptClient.connect();
        //mqttAcceptClient.subscribe("test_queue",0);
        return mqttAcceptClient;
    }

}
