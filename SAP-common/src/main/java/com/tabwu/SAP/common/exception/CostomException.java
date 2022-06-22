package com.tabwu.SAP.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: SAP-innosen
 * @Author: tabwu
 * @Date: 2022/6/11 15:14
 * @Description: 自定义异常对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostomException extends RuntimeException{
    private int code;
    private String message;
}
