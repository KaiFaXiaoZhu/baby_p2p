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
    private String userId;      //用户id
    private String realname;    //开户人姓名
    private String bankName;    //银行名称
    private String cardNumber;  //银行卡号
    private String branchName;  //支行名称
    private Integer balance;     //可用余额
    private Date createTime;    //创建日期
}

