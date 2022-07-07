package com.tabwu.SAP.sale;

import com.tabwu.SAP.common.constant.RabbitStaticConstant;
import com.tabwu.SAP.common.entity.MqMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/6 11:27
 * @DESCRIPTION:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

   @Test
    public void rabbitTest() {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("yls-20220705094955-7");
        MqMsg mqMsg = new MqMsg();
        mqMsg.setPayStatus(true);
        mqMsg.setItemId("1544561249777856514");
        mqMsg.setCode("yls-20220705094955-7");
/*        mqMsg.setTax("13%");
        mqMsg.setAllTax(new BigDecimal(26));
        mqMsg.setAllPrice(new BigDecimal(74));
        mqMsg.setTotalPrice(new BigDecimal(100));*/
        rabbitTemplate.convertAndSend(RabbitStaticConstant.SALE_TOPIC_EXCHANGE,"sale.pay",mqMsg,correlationData);
    }
}
