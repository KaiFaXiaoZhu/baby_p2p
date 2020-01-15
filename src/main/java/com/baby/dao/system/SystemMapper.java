package com.baby.dao.system;

import com.baby.pojo.SystemDictionaryItem;

import java.util.List;

public interface SystemMapper {

    /**
     * 数据字典获取
     * @return
     */
    public List<SystemDictionaryItem> selectDictionaryItem() throws Exception;

}
