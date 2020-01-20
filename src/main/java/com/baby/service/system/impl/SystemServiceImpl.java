package com.baby.service.system.impl;

import com.baby.common.IdUtils;
import com.baby.common.StringUtil;
import com.baby.common.orderUtil;
import com.baby.dao.system.SystemMapper;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.BankCard;
import com.baby.pojo.Recharge;
import com.baby.pojo.SystemDictionaryItem;
import com.baby.pojo.UserAccount;
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
    public List<SystemDictionaryItem> selectDictionaryItem() throws Exception {
        return systemMapper.selectDictionaryItem();
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
}
