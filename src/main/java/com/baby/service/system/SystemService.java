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
    public List<SystemDictionaryItem> selectDictionaryItem() throws Exception;
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
}
