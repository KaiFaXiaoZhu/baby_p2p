package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private Integer account_id;    //账户id
    private String avatar;         //肖像图片
    private String realname;       //真实姓名
    private String id_card_number; //身份证号码
    private String phone_number;   //手机号码
    private int income_level_id;   //收入等级id
    private int marriage_id;       //婚姻状况id
    private int edu_background_id; //教育背景id
    private int house_condition_id;//住房情况id
    private Date create_time;      //创建时间
}
