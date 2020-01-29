package com.baby.service.user.impl;

import com.baby.common.IPUtil;
import com.baby.common.IdUtils;
import com.baby.common.StringUtil;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.*;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 验证用户名唯一性
     *
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public boolean getbabyUserByname(String username) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        if (userMapper.getbabyUserMap(map) != null) {
            return false;
        }
        return true;
    }

    /**
     * 用户注册
     *
     * @param userAccount
     * @return
     * @throws Exception
     */
    @Override
    public boolean insertbabyUser(UserAccount userAccount) throws Exception {
        boolean flag = false;
        if (userMapper.insertbabyUser(userAccount) != 1) {
            throw new Exception("注册失败");
        } else {
            //封装账号信息,赠送10000元体验金报存在可用金额中
            UserWallet userWallet = new UserWallet(userAccount.getId(), 1000000, 0, 0, 0, 0, 0, 0, 0, new Date());
            //封装身份信息
            UserInfo userInfo = new UserInfo(userAccount.getId(), "nobody.jpg", "", "", "", 0, 0, 0, 0, new Date());
            if (userMapper.insertBabyUserwallet(userWallet) == 1 && userMapper.insertBabyUserInfo(userInfo) == 1) {   //插入一条新用户的账号信息
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 新增用户登录记录
     *
     * @param username
     * @return
     */
    @Override
    public UserAccount loginUser(String username, String password, HttpServletRequest request) throws Exception {
        UserAccount userAccount = null;
        if (username != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            try {
                //根据用户名获取用户信息
                userAccount = userMapper.getbabyUserMap(map);
                //封装登录记录信息
                LoginLog log = new LoginLog();
                log.setIp(IPUtil.getIp(request));
                log.setLoginTime(new Date());
                log.setUsername(userAccount.getUsername());
                log.setAccountType(userAccount.getAccountType());
                log.setCreateTime(new Date());
                if (userAccount.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                    //登录成功，记录结果为1
                    log.setLoginResult(1);
                    //修改用户信息的最后登录时间
                    userAccount.setLastLoginTime(new Date());
                    if (userMapper.updatebabyUser(userAccount) != 1) {
                        userAccount = null;
                    }
                } else {
                    //失败为0
                    userAccount = null;  //把userAccount对象赋为空方便controller判断
                    log.setLoginResult(0);
                }
                if (userMapper.insertbabyUserlog(log) != 1) {
                    userAccount = null;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userAccount;
    }

    /**
     * 账户钱包获取
     *
     * @param account_id
     * @return
     */
    @Override
    public UserWallet selectBabyUserwallet(String account_id){
        UserWallet userWallet = null;
        if (account_id != null) {
            try {
                userWallet = userMapper.selectBabyUserwallet(account_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userWallet;
    }

    /**
     * 用户基本信息获取
     *
     * @param account_id
     * @return
     * @throws Exception
     */
    @Override
    public UserInfo selectBabyUserInfo(String account_id){
        UserInfo userInfo = null;
        if (account_id != null) {
            try {
                userInfo = userMapper.selectBabyUserInfo(account_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    /**
     * 用户信息修改
     *
     * @param userInfo
     * @return
     */
    @Override
    public boolean updateBabyUserInfo(UserInfo userInfo) throws Exception {
        boolean flag = false;
        Map<String, Object> result = new HashMap<>();
        result.put("id", userInfo.getAccountId());
        UserAccount userAccount = userMapper.getbabyUserMap(result);
        if (userAccount.getFillUserinfo() == 0) {
            UserWallet userWallet = new UserWallet(userInfo.getAccountId(), null, null, null, null, null, 100, 10000, null, null);
            userAccount.setFillUserinfo(1);
            if (userMapper.updateBabyUserwallt(userWallet) == 1 && userMapper.updatebabyUser(userAccount) == 1) {
                flag = true;
            }
        } else {
            if (userMapper.updateBabyUserInfo(userInfo) == 1) {
                flag = true;
            }
        }
        return flag;
    }
    /**
     * 查询钱包内是否有足够的还款金额
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Integer getavailableAmount(String id, String userId) {
        Integer availableAmount = null;
        if(id!=null&&userId!=null){
            availableAmount =  userMapper.getavailableAmount(id,userId);
        }
        return availableAmount;
    }
    /**
     * 银行卡信息的获取
     * @param userid
     * @return
     */
    @Override
    public BankCard selectBabyBankCard(String userid) {
        Map<String,Object> result = new HashMap<>();
        BankCard bankCard = null;
        if(!StringUtil.isEmpty(userid)){
            result.put("userId",userid);
            try {
                bankCard = userMapper.selectBabyBankCard(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bankCard;
    }
    /**
     * 银行卡绑定
     * @param bankCard
     * @return
     * @throws Exception
     */
    @Override
    public boolean insertBabyBankCard(BankCard bankCard) throws Exception {
        boolean flag = false;
        if(!StringUtil.isEmpty(bankCard)){
            bankCard.setId(IdUtils.getUUID());
            bankCard.setBalance(10000);
            bankCard.setCreateTime(new Date());
            if(userMapper.insertBabyBankCard(bankCard) == 1){
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 增加还款人的信用额度
     * @param id
     * @param userId
     * @return
     */
    @Override
    public int IncreaseCreditLine(String id, String userId) {
        Integer IncreaseCreditLine = null;
        if(id!=null&&userId!=null){
            IncreaseCreditLine =  userMapper.IncreaseCreditLine(id,userId);
        }
        return IncreaseCreditLine;
    }

    /**
     * 借款人收到还款金额
     * @param id
     * @param userId
     * @return
     */
    @Override
    public int RevenueUpdate(String id, String userId) {
        Integer RevenueUpdate = null;
        if(id!=null&&userId!=null){
            RevenueUpdate =  userMapper.RevenueUpdate(id,userId);
        }
        return RevenueUpdate;
    }

    /**
     * 余额提现
     * @param withdraw
     * @return
     */
    @Override
    public boolean insertBabyWithdraw(Withdraw withdraw,UserWallet userWallet) {
        boolean flag = false;
        if(!StringUtil.isEmpty(withdraw)){
            //账户信息封装
            //对账户余额进行修改（可用余额-（提现余额+手续费））
            userWallet.setAvailableAmount(userWallet.getAvailableAmount()-(withdraw.getAmount()+withdraw.getFee()));
            try {
                if(userMapper.insertBabyWithdraw(withdraw) == 1 && userMapper.updateBabyUserwallt(userWallet) == 1){
                    flag = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    /**
     * 账户流水数’据‘获取
     * @param beginDate
     * @param endDate
     * @param userId
     * @param current
     * @param size
     * @return
     */
    @Override
    public List<AccountFlow> selectBabyaccountFlow(String beginDate, String endDate, String userId, Integer current, Integer size) {
        Map<String,Object> map = new HashMap<>(); //条件集合
        List<AccountFlow> accountFlows = null;
        if(!StringUtil.isEmpty(beginDate)){
            map.put("beginDate",beginDate);
        }
        if(!StringUtil.isEmpty(endDate)){
            map.put("endDate",endDate);
        }
        if(!StringUtil.isEmpty(userId)){
            map.put("userId",userId);
        }
        map.put("current",current);
        map.put("size",size);
        try {
            accountFlows = userMapper.selectBabyaccountFlow(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountFlows;
    }
    /**
     * 账户流水数’量‘获取
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     */
    @Override
    public Integer selectBabyaccountFlowCount(String beginDate, String endDate, String userId) {
        Map<String,Object> map = new HashMap<>(); //条件集合
        Integer num = null;
        if(!StringUtil.isEmpty(beginDate)){
            map.put("beginDate",beginDate);
        }
        if(!StringUtil.isEmpty(endDate)){
            map.put("endDate",endDate);
        }
        if(!StringUtil.isEmpty(userId)){
            map.put("userId",userId);
        }
        try {
            num = userMapper.selectBabyaccountFlowCount(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int DeductCreditScore(int creditScore, Integer creditLine, Integer residualCreditLine, String accountId) {
        return userMapper.DeductCreditScore(creditScore,creditLine,residualCreditLine,accountId);
    }


}
