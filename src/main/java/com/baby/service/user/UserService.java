package com.baby.service.user;

import com.baby.pojo.UserAccount;
import com.baby.pojo.UserInfo;
import com.baby.pojo.UserWallet;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

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
    public UserWallet selectBabyUserwallet(String account_id) throws Exception;

    /**
     * 用户基本信息获取
     * @param account_id
     * @return
     */
    public UserInfo selectBabyUserInfo(String account_id) throws Exception;
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




}
