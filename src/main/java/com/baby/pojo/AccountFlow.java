package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountFlow {
    private Integer id;          //Id
    private String  accountId;  //账户Id
    private Integer amount;     //金额(单位：分)
    private int flowType;   //资金流水类型
    private Integer availableAmount;    //(变化后)可用金额(单位：分)
    private Integer freezeAmount;        //(变化后)冻结金额(单位：分)
    private String remark;       //流水说明
    private Date createTime;    //创建日期
}
