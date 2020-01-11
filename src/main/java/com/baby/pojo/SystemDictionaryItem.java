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
    private int parent_id;     //父id
    private String value;      //字典项值
    private int order_no;      //排序号(正序)
    private Date create_time;  //创建时间
}
