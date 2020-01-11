package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginLog {
    private Integer id;                  //id
    private String ip;                   //客户端ip地址
    private Date login_time;            //登录时间
    private String username;            //登录用户名
    private Integer account_type;       //账户类型(1:前台用户, 2:运营人员)',
    private Integer login_result;       //登录结果(1:成功，0:失败)
    private Date create_time;           //创建时间
}
