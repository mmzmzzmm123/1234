package com.hxzh.front.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String MQ_DETECTION = "mq.detection";
    public static final String EXCHANGE_TOPICS_INFO="exchange_topics_inform";

    /**
     * 主题模式交换机
     *
     * @return {@link Exchange}
     */
    @Bean(EXCHANGE_TOPICS_INFO)
    public Exchange exchangeTopicInfo(){
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFO).durable(true).build();
    }

    /**
     * 检测队列
     *
     * @return {@link Queue}
     */
    @Bean(MQ_DETECTION)
    public Queue mqDetection(){
        return new Queue(MQ_DETECTION);
    }
    public Binding bindQueueToExchange(Queue queue, Exchange exchange, String routingKey){
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

}
