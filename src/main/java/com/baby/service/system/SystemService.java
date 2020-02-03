package com.baby.service.system;

import com.baby.pojo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SystemService {
    /**
     * 数据字典获取
     * @return
     */
    public List<SystemDictionaryItem> selectDictionaryItem(Map<String,Object> result);
    /**
     * 账户充值
     * @param recharge
     * @return
     * @throws Exception
     */
    public boolean insertRecharge(Recharge recharge, UserAccount userAccount);
    /**
     * 充值记录获取
     * @return
     */
    public List<Recharge> selectRecharge(String beginDate, String endDate,Integer state, String userId, Integer current, Integer size);
    /**
     * 充值记录数目获取
     * @return
     */
    public Integer selectRechargeCount(String beginDate,String endDate,Integer state,String userId);
    /**
     * 登录记录获取
     * @return
     * @throws Exception
     */
    public List<LoginLog> selectLoginLog(String beginDate,String endDate,Integer loginResult,String username,Integer current,Integer size);

    /**
     * 充值记录数目获取
     * @return
     */
    public Integer selectLoginLogCount(String beginDate,String endDate,Integer loginResult,String username);

    /**
     * 数据字典分组获取
     */
    public List<SystemDictionary> selectDictionary(Map<String,Object> result);

    /**
     *数据字典数量获取
     */
    public Integer selectDictionaryItemcount(Map<String,Object> result);

    /**
     * 新增数据字典
     * @param systemDictionaryItem
     * @return
     */
    public boolean insertDictionaryItem(SystemDictionaryItem systemDictionaryItem);

    /**
     * 修改数据字典
     * @param systemDictionaryItem
     * @return
     */
    public boolean updatedictionaryItem(SystemDictionaryItem systemDictionaryItem);

    /**
     * 获取数据字典项数量
     * @return
     * @throws Exception
     */
    public Integer selectdictionarycount(Map<String,Object> result);

    /**
     * 添加数据字典项
     * @param systemDictionary
     * @return
     */
    public boolean insertdictionary(SystemDictionary systemDictionary);

    /**
     * 修改数据字典项
     * @param systemDictionary
     * @return
     * @throws Exception
     */
    public boolean updatedictionary(SystemDictionary systemDictionary);

    /**
     * 充值记录审核
     * @param recharge
     * @return
     */
    public boolean updaterecharge(Recharge recharge);
}
