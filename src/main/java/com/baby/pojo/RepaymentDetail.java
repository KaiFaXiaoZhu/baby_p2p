package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepaymentDetail {
    private String id;                      //id
    private String bidId;                 //标的id
    private String borrowId;             //借款id
    private String repaymentId;         //还款id
    private String borrowUserId;      //借款人id
    private String bidUserId;         //投标人id
    private String borrowTitle;        //借款标题
    private Integer totalAmount;       //本期还款总金额(利息+本金)(单位：分)
    private Integer principal;          //本期还款本金(单位；分)
    private Integer interest;           //本期还款总利息(单位：分)
    private int period;                 //还款期数(第几月还款)
    private Date deadline;              //本期还款截止日期
    private Date repaymentTime;       //还款时间
    private int repaymentType;       //还款方式
    private Date createTime;          //创建时间
}
