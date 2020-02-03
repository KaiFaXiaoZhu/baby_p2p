package com.baby.dao.system;

import com.baby.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMapper {

    /**
     * 数据字典获取
     * @return
     */
    public List<SystemDictionaryItem> selectDictionaryItem(Map<String,Object> result) throws Exception;

    /**
     * 账户充值
     * @param recharge
     * @return
     * @throws Exception
     */
    public Integer insertRecharge(Recharge recharge) throws Exception;

    /**
     * 充值记录获取
     * @return
     * @throws Exception
     */
    public List<Recharge> selectRecharge(Map<String,Object> result) throws Exception;

    /**
     * 充值记录数目获取
     * @return
     */
    public Integer selectRechargeCount(Map<String,Object> result) throws Exception;

    /**
     * 登录记录获取
     * @param result
     * @return
     * @throws Exception
     */
    public List<LoginLog> selectLoginLog(Map<String,Object> result) throws Exception;

    /**
     * 充值记录数目获取
     * @return
     */
    public Integer selectLoginLogCount(Map<String,Object> result) throws Exception;

    /**
     * 数据字典分组获取
     */
    public List<SystemDictionary> selectDictionary() throws Exception;

    /**
     *数据字典数量获取
     */
    public Integer selectDictionaryItemcount(Map<String,Object> result) throws Exception;

    /**
     * 新增数据字典
     * @param systemDictionaryItem
     * @return
     */
    public Integer insertDictionaryItem(SystemDictionaryItem systemDictionaryItem) throws Exception;

    /**
     * 修改数据字典
     * @param systemDictionaryItem
     * @return
     */
    public Integer updatedictionaryItem(SystemDictionaryItem systemDictionaryItem) throws Exception;

}
