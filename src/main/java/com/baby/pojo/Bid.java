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
    private String borrowId;      //借款id
    private String borrowTitle;   //借款标题
    private Integer bidAmount;    //投标金额
    private Integer bidInterest;  //投标利息
    private int yearRate;         //年化率
    private int borrowState;     //借款状态
    private String bidUserId;    //投标人id
    private String bidUsername;   //投标人用户名
    private Date bidTime;         //投标时间
    private Date createTime;      //创建日期
}
