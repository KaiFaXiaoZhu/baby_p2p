package com.baby.service.user.impl;

import com.baby.common.IPUtil;
import com.baby.common.StringUtil;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.LoginLog;
import com.baby.pojo.UserAccount;
import com.baby.pojo.UserInfo;
import com.baby.pojo.UserWallet;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
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
            UserWallet userWallet = new UserWallet(userAccount.getId(), 10000, 0, 0, 0, 0, 0, 0, 0, new Date());
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
    public UserWallet selectBabyUserwallet(String account_id) throws Exception {
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
    public UserInfo selectBabyUserInfo(String account_id) throws Exception {
        UserInfo userInfo = null;
        if (account_id != null) {
            userInfo = userMapper.selectBabyUserInfo(account_id);
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

    @Override
    public Integer getavailableAmount(String id, String userId) {
        Integer availableAmount = null;
//        if(id!=null&&userId!=null){
//            availableAmount =  userMapper.getavailableAmount(id,userId);
//        }
        return availableAmount;
    }

}
