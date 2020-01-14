package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemDictionary {
    private int id;  //字典组id
    private String code;  //字典组编号
    private String name; //字典组名称
    private Date createTime;  //创建时间
}
