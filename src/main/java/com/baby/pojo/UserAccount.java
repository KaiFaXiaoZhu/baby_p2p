package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccount {
    private String id;            //账户id
    private String username;      //用户名
    private String password;      //密码
    private int accountStatus;   //状态( 0:禁用, 1:正常 )
    private int accountType;     //账户类型( 1:普通用户, 2:运营人员 )
    private int fillUserinfo;    //是否完善个人信息( 0:未完善, 1:已完善 )
    private Date lastLoginTime; //最后登录时间
    private Date createTime;     //创建时间
}
