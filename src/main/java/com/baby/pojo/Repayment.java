package com.baby.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repayment {
    private String id;                              //id
    private String borrowId;                     //借款id
    private String borrowUserId;               //借款人id
    private String borrowTitle;                 //借款标题
    private Date deadline;                       //截止日期
    private Date repaymentTime;                //还款日期
    private Integer totalAmount;               //本期还款总金额(单位：分)
    private Integer principal;                  //本期还款本金(单位：分)
    private Integer interest;                   //本期还款利息(单位：分)
    private int period;                         //还款期数(第几期)
    private int state;                          //还款状态
    private int borrowType;                   //借款类型
    private int repaymentType;               //还款方式
    private Date createTime;                  //创建时间
}
