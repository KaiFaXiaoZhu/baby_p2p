package com.baby.dao.user;

import com.baby.pojo.LoginLog;
import com.baby.pojo.UserAccount;
import com.baby.pojo.UserInfo;
import com.baby.pojo.UserWallet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    public Integer updateBabyUserwallt(UserWallet userWallet);




}
