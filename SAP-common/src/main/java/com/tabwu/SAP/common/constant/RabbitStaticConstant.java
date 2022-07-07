package com.tabwu.SAP.common.constant;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/7/5 16:53
 * @DESCRIPTION:
 */
public class RabbitStaticConstant {

    public static final String SALE_TOPIC_EXCHANGE = "saleTopicExchange";
    public static final String PURCHASE_TOPIC_EXCHANGE = "purchaseTopicExchange";

    public static final String PURCHASE_PAY_QUEUE = "purchasePayQueue";
    public static final String PURCHASE_PAY_SUCCESS_QUEUE = "purchasePaySuccessQueue";

    public static final String SALE_RECEIPT_QUEUE = "saleReceiptQueue";
    public static final String SALE_RECEIPT_SUCCESS_QUEUE = "saleReceiptSuccessQueue";
    public static final String SALE_REFUND_QUEUE = "saleRefundQueue";


}
