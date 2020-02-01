package com.baby.service.user;

import com.baby.pojo.*;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 用户注册用户名唯一
     * @param username
     * @return
     */
    public boolean getbabyUserByname(String username) throws Exception;

    /**
     * 新增用户
     * @param userAccount
     * @return
     * @throws Exception
     */
    public boolean insertbabyUser(UserAccount userAccount) throws Exception;


    /**
     * 新增用户登录记录
     * @param
     * @return
     */
    public UserAccount loginUser(String username, String password, HttpServletRequest request) throws Exception;

    /**
     * 账户钱包获取
     * @param account_id
     * @return
     */
    public UserWallet selectBabyUserwallet(String account_id);

    /**
     * 用户基本信息获取
     * @param account_id
     * @return
     */
    public UserInfo selectBabyUserInfo(String account_id);
    /**
     * 用户信息修改
     * @param userInfo
     * @return
     */
    public boolean updateBabyUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 查询钱包内是否有足够的还款金额
     * @param id
     * @param userId
     * @return
     * @throws Exception
     */
    public Integer getavailableAmount(String id,String userId);
    /**
     * 银行卡信息的获取
     * @param userid
     * @return
     */
    public BankCard selectBabyBankCard(String userid) throws Exception;
    /**
     * 银行卡绑定
     * @param bankCard
     * @return
     * @throws Exception
     */
    public boolean insertBabyBankCard(BankCard bankCard) throws Exception;

    /**
     *增加还款人的信用额度
     * @param id
     * @param userId
     * @return
     */
    public int IncreaseCreditLine(String id,String userId);

    /**
     * 借款人收到还款金额
     * @param id
     * @param userId
     * @return
     */
    public int RevenueUpdate(String id,String userId);
    /**
     * 余额提现
     * @param withdraw
     * @return
     */
    public boolean insertBabyWithdraw(Withdraw withdraw,UserWallet userWallet);

    /**
     * 账户流水数’据‘获取
     * @param beginDate
     * @param endDate
     * @param userId
     * @param current
     * @param size
     * @return
     */
    public List<AccountFlow> selectBabyaccountFlow(String beginDate,String endDate,String userId,Integer current,Integer size);

    /**
     * 账户流水数’量‘获取
     * @param beginDate
     * @param endDate
     * @param userId
     * @return
     */
    public Integer selectBabyaccountFlowCount(String beginDate,String endDate,String userId);

    /**
     * 用户账号钱包信息修改
     * @param userWallet
     * @return
     */
    public Integer updateBabyUserwallt(UserWallet userWallet) throws Exception;


    /**
     * 扣除逾期方的信用得分和授信额度与剩余授信额度
     */
    public int DeductCreditScore(int creditScore,Integer creditLine,Integer residualCreditLine,String accountId);

}
