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
    private String borrow_id;                     //借款id
    private String borrow_user_id;               //借款人id
    private String borrow_title;                 //借款标题
    private Date deadline;                       //截止日期
    private Date repayment_time;                //还款日期
    private Integer total_amount;               //本期还款总金额(单位：分)
    private Integer principal;                  //本期还款本金(单位：分)
    private Integer interest;                   //本期还款利息(单位：分)
    private int period;                         //还款期数(第几期)
    private int state;                          //还款状态
    private int borrow_type;                   //借款类型
    private int repayment_type;               //还款方式
    private Date create_time;                  //创建时间
}
