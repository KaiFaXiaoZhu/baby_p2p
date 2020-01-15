package com.baby.service.system.impl;

import com.baby.dao.system.SystemMapper;
import com.baby.pojo.SystemDictionaryItem;
import com.baby.service.system.SystemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
    @Resource
    SystemMapper systemMapper;

    /**
     * 数据字典获取
     * @return
     */
    @Override
    public List<SystemDictionaryItem> selectDictionaryItem() throws Exception {
        return systemMapper.selectDictionaryItem();
    }
}
