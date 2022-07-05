package com.tabwu.SAP.sale.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/5 16:07
 * @DESCRIPTION:
 */
@Slf4j
@Configuration
public class RabbitConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    private RabbitTemplate rabbitTemplate;

    @Primary
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


    @Bean
    // 当消息为对象时，使用Jackson序列化消息
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }


    /**
     * 1、只要消息抵达Broker就ack=true
     * @param correlationData 当前消息的唯一关联数据(这个是消息的唯一id)
     * @param ack 消息是否成功收到
     * @param cause 失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(correlationData + "====>" + ack + "====>" + "cause");
    }


    /**
     * 只要消息没有投递给指定的队列，就触发这个失败回调
     * @param message  投递失败的消息详细信息
     * @param code  回复的状态码
     * @param replyText  回复的文本内容
     * @param exchange 当时这个消息是哪个交换机发送的
     * @param routingKey 当时这个消息用哪个路邮键
     */
    @Override
    public void returnedMessage(Message message, int code, String replyText, String exchange, String routingKey) {
        log.error(message.toString());
        log.error(code + "");
        log.error(replyText);
        log.error(exchange);
        log.error(routingKey);
    }
}
