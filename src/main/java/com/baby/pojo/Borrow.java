package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Borrow {
    private String id;                  //id
    private String borrowUserId;    //借款人id
    private String borrowUsername;   //借款人用户名
    private String title;               //借款标题
    private String description;        //借款描述
    private int repaymentType;       //还款类型( 1.等额本息  2.先息后本 )
    private int borrowType;          //借款类型( 1.信用贷 2.车贷 3.房贷 )
    private int borrowState;         //借款状态
    private Integer borrowAmount;    //借款总金额(单位：分)
    private int yearRate;            //年化率
    private int repaymentMonth;     //还款期数
    private int bidNum;              //已投标数量
    private Integer totalInterest;  //总回报金额(单位：分)
    private Integer currentBidAmount;    //当前已投标金额(单位：分)
    private Integer currentBidInterest;  //当前已投标利息(单位：分)
    private Date bidDeadline;       //招标截止日期
    private int bidDays;            //招标天数
    private Date applyTime;         //申请时间
    private Date publishTime;       //发标时间
    private Date createTime;        //创建时间
}
