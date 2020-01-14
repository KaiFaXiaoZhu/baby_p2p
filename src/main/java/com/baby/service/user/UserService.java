package com.baby.service.user;

import com.baby.pojo.UserAccount;

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

}
