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
    private String borrow_user_id;    //借款人id
    private String borrow_username;   //借款人用户名
    private String title;               //借款标题
    private String description;        //借款描述
    private int repayment_type;       //还款类型( 1.等额本息  2.先息后本 )
    private int borrow_type;          //借款类型( 1.信用贷 2.车贷 3.房贷 )
    private int borrow_state;         //借款状态
    private Integer borrow_amount;    //借款总金额(单位：分)
    private int year_rate;            //年化率
    private int repayment_month;     //还款期数
    private int bid_num;              //已投标数量
    private Integer total_interest;  //总回报金额(单位：分)
    private Integer current_bid_amount;    //当前已投标金额(单位：分)
    private Integer current_bid_interest;  //当前已投标利息(单位：分)
    private Date bid_deadline;       //招标截止日期
    private int bid_days;            //招标天数
    private Date apply_time;         //申请时间
    private Date publish_time;       //发标时间
    private Date create_time;        //创建时间
}
