package com.tabwu.SAP.purchase.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Autowired
    private RabbitTemplate rabbitTemplate;


    // 声明创建exchange、queue、与binding关系
    /**
     * 采购管理交换机
     * @return
     */
    @Bean
    public Exchange purchaseTopicExchange() {
        return new TopicExchange("purchaseTopicExchange",true,false,null);
    }

    /**
     * 采购付款队列，采购服务发生，财务服务监听
     * @return
     */
    @Bean
    public Queue purchasePayQueue() {
        return new Queue("purchasePayQueue",true,false,false,null);
    }

    /**
     * 采购付款成功队列，财务务发生，采购服务监听
     * @return
     */
    @Bean
    public Queue purchasePaySuccessQueue() {
        return new Queue("purchasePaySuccessQueue",true,false,false,null);
    }

    /**
     * 采购退货收款队列，采购服务发生，财务服务监听，我司退货成功后发送
     * @return
     */
    @Bean
    public Queue purchaseReceiptQueue() {
        return new Queue("purchaseReceiptQueue",true,false,false,null);
    }


    /**
     * 采购退货收款成功队列，财务服务发生，采购服务监听
     * @return
     */
    @Bean
    public Queue purchaseReceiptSuccessQueue() {
        return new Queue("purchaseReceiptSuccessQueue",true,false,false,null);
    }


    @Bean
    public Binding payBinding() {
        return new Binding("purchasePayQueue", Binding.DestinationType.QUEUE,"purchaseTopicExchange","#.purchase.pay.#",null);
    }


    @Bean
    public Binding paySuccessBinding() {
        return new Binding("purchasePaySuccessQueue", Binding.DestinationType.QUEUE,"purchaseTopicExchange","#.purchase.pay.success.#",null);
    }

    @Bean
    public Binding receiptBinding() {
        return new Binding("purchaseReceiptQueue", Binding.DestinationType.QUEUE,"purchaseTopicExchange","#.purchase.receipt.#",null);
    }

    @Bean
    public Binding receiptSuccessBinding() {
        return new Binding("purchaseReceiptSuccessQueue", Binding.DestinationType.QUEUE,"purchaseTopicExchange","#.purchase.receipt.success.#",null);
    }




    @Bean
    // 当消息为对象时，使用Jackson序列化消息
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @PostConstruct
    public void initRabbitTemplate() {
        rabbitTemplate.setMessageConverter(messageConverter());
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
        System.out.println("消息发送到broker成功===>" + correlationData.getId() + "====>" + ack + "====>" + "cause");
        // 消息发送失败时补偿考虑方向，可以在发送消息时，将消息同时在数据库中存一份，当消息发送失败时，可以根据消息id取出消息重新发送补偿
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
        log.error("消息发送到队列失败");
        log.error(message.toString());
        log.error(code + "");
        log.error(replyText);
        log.error(exchange);
        log.error(routingKey);
    }
}
