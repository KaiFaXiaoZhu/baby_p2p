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
    private String  account_id;  //账户Id
    private Integer amount;     //金额(单位：分)
    private int flow_type;   //资金流水类型
    private Integer available_amount;    //(变化后)可用金额(单位：分)
    private Integer freeze_amount;        //(变化后)冻结金额(单位：分)
    private String remark;       //流水说明
    private Date create_time;    //创建日期
}
