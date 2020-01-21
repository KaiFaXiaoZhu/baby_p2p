package com.baby.dao.user;

import com.baby.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户mapper层
 */
public interface UserMapper {
    /**
     * 用户账号信息获取
     * @param user
     * @return
     */
    public UserAccount getbabyUserMap(Map<String, Object> user) throws Exception;

    /**
     * 用户注册
     * @param userAccount
     * @return
     */
    public Integer insertbabyUser(UserAccount userAccount) throws Exception;

    /**
     * 新增用户登录记录
     * @param loginLog
     * @return
     */
    public Integer insertbabyUserlog(LoginLog loginLog) throws Exception;

    /**
     * 用户账号更改
     * @param userAccount
     * @return
     */
    public Integer updatebabyUser(UserAccount userAccount) throws Exception;

    /**
     * 新增用户身份信息记录
     * @param userInfo
     * @return
     */
    public Integer insertBabyUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 新增账户钱包记录
     * @param userWallet
     * @return
     */
    public Integer insertBabyUserwallet(UserWallet userWallet) throws Exception;

    /**
     * 账户钱包获取
     * @param account_id
     * @return
     */
    public UserWallet selectBabyUserwallet(@Param("accountId") String account_id) throws Exception;

    /**
     * 用户基本信息获取
     * @param account_id
     * @return
     */
    public UserInfo selectBabyUserInfo(@Param("accountId") String account_id) throws Exception;

    /**
     * 用户信息修改
     * @param userInfo
     * @return
     */
    public Integer updateBabyUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 用户账号钱包信息修改
     * @param userWallet
     * @return
     */
    public Integer updateBabyUserwallt(UserWallet userWallet) throws Exception;
    /**
     * 查询钱包内是否有足够的还款金额
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer getavailableAmount(@Param("id") String id,@Param("userId") String userId);

    /**
     * 银行卡信息的获取
     * @param map
     * @return
     */
    public BankCard selectBabyBankCard(Map<String,Object> map) throws Exception;

    /**
     * 银行卡绑定
     * @param bankCard
     * @return
     * @throws Exception
     */
    public Integer insertBabyBankCard(BankCard bankCard) throws Exception;


    /**
     *增加还款人的信用额度
     * @param id
     * @param userId
     * @return
     */
    public int IncreaseCreditLine(@Param("id") String id,@Param("userId") String userId);

    /**
     * 借款人收到还款金额
     * @param id
     * @param userId
     * @return
     */
    public int RevenueUpdate(@Param("id") String id,@Param("userId") String userId);

    /**
     * 余额提现
     * @param withdraw
     * @return
     */
    public Integer insertBabyWithdraw(Withdraw withdraw) throws Exception;

    /**
     * 账户流水数’据‘获取
     * @param result
     * @return
     * @throws Exception
     */
    public List<AccountFlow> selectBabyaccountFlow(Map<String,Object> result) throws Exception;

    /**
     * 账户流水数’量‘获取
     * @param result
     * @return
     * @throws Exception
     */
    public Integer selectBabyaccountFlowCount(Map<String,Object> result) throws Exception;

}
