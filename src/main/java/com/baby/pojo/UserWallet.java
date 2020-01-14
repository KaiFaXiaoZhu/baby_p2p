package com.baby.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet {
    private String accountId;             //账户id
    private Integer availableAmount;      //可用金额(单位：分)
    private Integer freezeAmount;         //冻结金额(单位：分)
    private Integer interestPending;      //待收利息(单位：分)
    private Integer principalPending;     //待收本金(单位：分)
    private Integer repaidAmount;         //待还金额(单位：分)
    private int creditScore;              //信用得分
    private Integer creditLine;           //授信额度(单位：分)
    private Integer residualCreditLine;  //剩余授信额度(单位：分)
    private Date createTime;              //创建时间
}
