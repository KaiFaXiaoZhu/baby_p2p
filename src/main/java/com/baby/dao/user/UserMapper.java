package com.baby.dao.user;

import com.baby.pojo.UserAccount;
import org.apache.ibatis.annotations.Mapper;

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
    public UserAccount getbabyUserMap(Map<String,Object> user) throws Exception;

    /**
     * 用户注册
     * @param userAccount
     * @return
     */
    public Integer insertbabyUser(UserAccount userAccount) throws Exception;

}
