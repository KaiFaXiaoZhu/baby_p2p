package com.baby.service.system.impl;

import com.baby.common.IdUtils;
import com.baby.common.StringUtil;
import com.baby.common.orderUtil;
import com.baby.dao.system.SystemMapper;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.*;
import com.baby.service.system.SystemService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
    @Resource
    SystemMapper systemMapper;
    @Resource
    UserMapper userMapper;


    /**
     * 数据字典获取
     * @return
     */
    @Override
    public List<SystemDictionaryItem> selectDictionaryItem(Map<String,Object> result){
        List<SystemDictionaryItem> systemDictionaryItems = null;
        try {
            systemDictionaryItems = systemMapper.selectDictionaryItem(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return systemDictionaryItems;
    }
    /**
     * 账户充值
     * @param recharge
     * @return
     * @throws Exception
     */
    @Override
    public boolean insertRecharge(Recharge recharge, UserAccount userAccount){
        boolean flag = false;
        if(!StringUtil.isEmpty(recharge)){
            //银行卡信息获取
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userAccount.getId());
            BankCard bankCard = null;
            try {
                bankCard = userMapper.selectBabyBankCard(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //充值订单信息组装
            recharge.setId(IdUtils.getUUID());
            recharge.setCreateTime(new Date());
            recharge.setTradeNo(orderUtil.getOrderCode());
            recharge.setUserId(userAccount.getId());
            recharge.setState(1);
            recharge.setRechargeTime(new Date());
            recharge.setBankCardId(bankCard.getId());
            try {
                if(systemMapper.insertRecharge(recharge) == 1){
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    /**
     * 充值记录获取
     * @return
     */
    @Override
    public List<Recharge> selectRecharge(String beginDate,String endDate,Integer state,String userId,Integer current,Integer size) {
        List<Recharge> rechargeList = null;
        Map<String,Object> map = new HashMap<>();
        try {
            if(!StringUtil.isEmpty(beginDate)){
                map.put("beginDate",beginDate);
            }
            if(!StringUtil.isEmpty(endDate)){
                map.put("endDate",endDate);
            }
            if(!StringUtil.isEmpty(state)){
                map.put("state",state);
            }
            if(!StringUtil.isEmpty(userId)){
                map.put("userId",userId);
            }
            if(!StringUtil.isEmpty(current)){
                map.put("current",current);
            }
            if(!StringUtil.isEmpty(size)){
                map.put("size",size);
            }
            rechargeList = systemMapper.selectRecharge(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rechargeList;
    }
    /**
     * 充值记录数目获取
     * @return
     */
    @Override
    public Integer selectRechargeCount(String beginDate,String endDate,Integer state,String userId) {
        Integer num = null;
        Map<String,Object> map = new HashMap<>();
        try {
            if(!StringUtil.isEmpty(beginDate)){
                map.put("beginDate",beginDate);
            }
            if(!StringUtil.isEmpty(endDate)){
                map.put("endDate",endDate);
            }
            if(!StringUtil.isEmpty(state)){
                map.put("state",state);
            }
            if(!StringUtil.isEmpty(userId)){
                map.put("userId",userId);
            }
            num = systemMapper.selectRechargeCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 登录记录获取
     * @param endDate
     * @param loginResult
     * @param username
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<LoginLog> selectLoginLog(String beginDate,String endDate, Integer loginResult, String username, Integer current, Integer size) {
       Map<String,Object> map = new HashMap<>();  //条件集合
       List<LoginLog> loginLogs = null;  //数据集合
       try {
           if(!StringUtil.isEmpty(beginDate)){
               map.put("beginDate",beginDate);
           }
           if(!StringUtil.isEmpty(endDate)){
               map.put("endDate",endDate);
           }
           if(!StringUtil.isEmpty(loginResult)){
               map.put("loginResult",loginResult);
           }
           if(!StringUtil.isEmpty(username)){
               map.put("username",username);
           }
           map.put("current",current);
           map.put("size",size);
           loginLogs = systemMapper.selectLoginLog(map);
       } catch (Exception e){
           e.printStackTrace();
       }
        return loginLogs;
    }

    /**
     * 登录记录数量获取
     * @param endDate
     * @param loginResult
     * @param username
     * @return
     */
    @Override
    public Integer selectLoginLogCount(String beginDate,String endDate, Integer loginResult, String username) {
        Map<String,Object> map = new HashMap<>();  //条件集合
        Integer count = null;  //数据集合
        try {
            if(!StringUtil.isEmpty(beginDate)){
                map.put("beginDate",beginDate);
            }
            if(!StringUtil.isEmpty(endDate)){
                map.put("endDate",endDate);
            }
            if(!StringUtil.isEmpty(loginResult)){
                map.put("loginResult",loginResult);
            }
            if(!StringUtil.isEmpty(username)){
                map.put("username",username);
            }
            count = systemMapper.selectLoginLogCount(map);
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }


    /**
     * 数据字典分组获取
     */
    @Override
    public List<SystemDictionary> selectDictionary(Map<String, Object> result) {
        List<SystemDictionary> dictionaries = null;
        try {
            dictionaries = systemMapper.selectDictionary(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaries;
    }

    /**
     *数据字典数量获取
     */
    @Override
    public Integer selectDictionaryItemcount(Map<String, Object> result) {
        Integer count = null;
        try {
            count = systemMapper.selectDictionaryItemcount(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 新增数据字典
     * @param systemDictionaryItem
     * @return
     */
    @Override
    public boolean insertDictionaryItem(SystemDictionaryItem systemDictionaryItem) {
        boolean flag = false;
        try {
            if(systemMapper.insertDictionaryItem(systemDictionaryItem) == 1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改数据字典
     * @param systemDictionaryItem
     * @return
     */
    @Override
    public boolean updatedictionaryItem(SystemDictionaryItem systemDictionaryItem) {
        boolean flag = false;
        try {
            if(systemMapper.updatedictionaryItem(systemDictionaryItem) == 1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 数据字典项数量获取
     * @param result
     * @return
     */
    @Override
    public Integer selectdictionarycount(Map<String, Object> result) {
        Integer count = null;
        try {
            count = systemMapper.selectdictionarycount(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 添加数据字典项
     * @param systemDictionary
     * @return
     */
    @Override
    public boolean insertdictionary(SystemDictionary systemDictionary) {
        boolean flag = false;
        try {
            if(systemMapper.insertdictionary(systemDictionary) == 1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 修改数据字典项
     * @param systemDictionary
     * @return
     */
    @Override
    public boolean updatedictionary(SystemDictionary systemDictionary) {
        boolean flag = false;
        try {
            if(systemMapper.updatedictionary(systemDictionary) == 1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 充值记录审核
     * @param recharge
     * @return
     */
    @Override
    public boolean updaterecharge(Recharge recharge) {
        boolean flag = false;
        try {
            if(systemMapper.updaterecharge(recharge) == 1){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
