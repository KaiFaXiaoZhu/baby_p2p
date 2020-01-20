package com.baby.dao.system;

import com.baby.pojo.BankCard;
import com.baby.pojo.LoginLog;
import com.baby.pojo.Recharge;
import com.baby.pojo.SystemDictionaryItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemMapper {

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

}
