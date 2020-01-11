package com.baby.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWallet {
    private String account_id;             //账户id
    private Integer available_amount;      //可用金额(单位：分)
    private Integer freeze_amount;         //冻结金额(单位：分)
    private Integer interest_pending;      //待收利息(单位：分)
    private Integer principal_pending;     //待收本金(单位：分)
    private Integer repaid_amount;         //待还金额(单位：分)
    private int credit_score;              //信用得分
    private Integer credit_line;           //授信额度(单位：分)
    private Integer residual_credit_line;  //剩余授信额度(单位：分)
    private Date create_time;              //创建时间
}
