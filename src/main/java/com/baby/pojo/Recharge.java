package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recharge {
    private String id;                  //id
    private String userId;            //充值账户id
    private String username;           //用户名
    private String bankCardId;      //银行卡id
    private String tradeNo;          //交易号
    private Integer amount;           //充值金额(单位：分)
    private Date rechargeTime;      //充值日期
    private int state;                //审核状态( 0:审核拒绝  1:审核中  2:审核通过 )
    private String remark;            //备注
    private Date createTime;        //创建日期
}
