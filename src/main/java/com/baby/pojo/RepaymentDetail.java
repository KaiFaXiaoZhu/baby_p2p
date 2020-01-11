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
    private String bid_id;                 //标的id
    private String borrow_id;             //借款id
    private String repayment_id;         //还款id
    private String borrow_user_id;      //借款人id
    private String bid_user_id;         //投标人id
    private String borrow_title;        //借款标题
    private Integer total_amount;       //本期还款总金额(利息+本金)(单位：分)
    private Integer principal;          //本期还款本金(单位；分)
    private Integer interest;           //本期还款总利息(单位：分)
    private int period;                 //还款期数(第几月还款)
    private Date deadline;              //本期还款截止日期
    private Date repayment_time;       //还款时间
    private int repayment_type;       //还款方式
    private Date create_time;          //创建时间
}
