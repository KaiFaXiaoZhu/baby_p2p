package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Withdraw {
    private String id;   //id
    private String user_id;  //提现账户id
    private Integer amount;  //提现金额(单位：分)
    private Integer fee;  //手续费(单位：分)
    private String bank_name; //银行名称
    private String realname; //开户人姓名
    private String card_number;  //银行卡号
    private String branch_name;  //支行名称
    private Date create_time;  //创建时间
}
