package com.baby.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class AccountFlow {
    private Integer id;          //Id
    private String  accountId;  //账户Id
    private Integer amount;     //金额(单位：分)
    private Integer flowType;   //资金流水类型
    private Integer availableAmount;    //(变化后)可用金额(单位：分)
    private Integer freezeAmount;        //(变化后)冻结金额(单位：分)
    private String remark;       //流水说明
    private Date createTime;    //创建日期

    public AccountFlow(){};

    public AccountFlow(Integer id, String accountId, Integer amount, Integer flowType, Integer availableAmount, Integer freezeAmount, String remark, Date createTime) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.flowType = flowType;
        this.availableAmount = availableAmount;
        this.freezeAmount = freezeAmount;
        this.remark = remark;
        this.createTime = createTime;
    }
}
