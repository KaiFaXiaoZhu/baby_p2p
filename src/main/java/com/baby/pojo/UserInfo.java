package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer accountId;    //账户id
    private String avatar;         //肖像图片
    private String realname;       //真实姓名
    private String idCardNumber; //身份证号码
    private String phoneNumber;   //手机号码
    private int incomeLevelId;   //收入等级id
    private int marriageId;       //婚姻状况id
    private int eduBackgroundId; //教育背景id
    private int houseConditionId;//住房情况id
    private Date createTime;      //创建时间
}
