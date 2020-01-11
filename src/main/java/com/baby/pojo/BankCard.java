package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCard {
    private String id;           //id
    private String user_id;      //用户id
    private String realname;    //开户人姓名
    private String bank_name;    //银行名称
    private String card_number;  //银行卡号
    private String branch_name;  //支行名称
    private Integer balance;     //可用余额
    private Date create_time;    //创建日期
}

