package com.tabwu.SAP.base.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tabwu
 * @since 2022-06-19
 */
@Data
@TableName("yls_customer_supplier")
@ApiModel(value = "CustomerSupplier对象", description = "")
@ExcelTarget("customerSupplier")
public class CustomerSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("类型  1--客户，2---供应商，3--潜在客户，默认-1")
    @Excel(name = "类型，1--客户，2---供应商，3--潜在客户，默认-1")
    private Integer type;

    @ApiModelProperty("分组id")
    @Excel(name = "分组id")
    private String groupId;

    @ApiModelProperty("公司名称")
    @Excel(name = "公司名称")
    private String companyName;

    @ApiModelProperty("联系人")
    @Excel(name = "联系人")
    private String contact;

    @ApiModelProperty("联系电话")
    @Excel(name = "联系电话")
    private Integer tel;

    @ApiModelProperty("电子邮箱")
    @Excel(name = "电子邮箱")
    private String email;

    @ApiModelProperty("传真")
    @Excel(name = "传真")
    private String fax;

    @ApiModelProperty("地址")
    @Excel(name = "地址")
    private String address;

    @ApiModelProperty("状态  0禁用  1启用")
    @Excel(name = "状态  0禁用  1启用")
    private Integer status;

    @ApiModelProperty("预收款")
    @Excel(name = "预收款")
    private BigDecimal advanceIn;

    @ApiModelProperty("期初应收")
    @Excel(name = "期初应收")
    private BigDecimal beginNeedGet;

    @ApiModelProperty("期初应付")
    @Excel(name = "期初应付")
    private BigDecimal beginNeedPay;

    @ApiModelProperty("all_need_get")
    @Excel(name = "累计应付")
    private BigDecimal allNeedGet;

    @ApiModelProperty("all_need_pay")
    @Excel(name = "累计应收")
    private BigDecimal allNeedPay;

    @ApiModelProperty("纳税人识别号")
    @Excel(name = "纳税人识别号")
    private String taxNum;

    @ApiModelProperty("开户行")
    @Excel(name = "开户行")
    private String bankName;

    @ApiModelProperty("账号")
    @Excel(name = "账号")
    private Integer accountNumber;

    @ApiModelProperty("税率")
    @Excel(name = "税率")
    private String taxRate;

    @ApiModelProperty("备注")
    @Excel(name = "备注")
    private String description;

    @ApiModelProperty("删除标记，0未删除，1删除，默认0")
    @Excel(name = "删除标记，0未删除，1删除，默认0")
    @TableLogic
    private Integer isDelete;


}
