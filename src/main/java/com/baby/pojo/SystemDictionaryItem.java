package com.baby.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemDictionaryItem {
    private int id;            //字典项id
    private int parentId;     //父id
    private String value;      //字典项值
    private int orderNo;      //排序号(正序)
    private Date createTime;  //创建时间
}
