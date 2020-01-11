package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bid {
    private String id;               //id
    private String borrow_id;      //借款id
    private String borrow_title;   //借款标题
    private Integer bid_amount;    //投标金额
    private Integer bid_interest;  //投标利息
    private int year_rate;         //年化率
    private int borrow_state;     //借款状态
    private String bid_user_id;    //投标人id
    private String bid_username;   //投标人用户名
    private Date bid_time;         //投标时间
    private Date create_time;      //创建日期
}
