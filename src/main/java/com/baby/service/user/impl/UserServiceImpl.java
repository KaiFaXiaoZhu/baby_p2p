package com.baby.service.user.impl;

import com.baby.dao.user.UserMapper;
import com.baby.pojo.UserAccount;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 验证用户名唯一性
     * @param username
     * @return
     * @throws Exception
     */
    @Override
    public boolean getbabyUserByname(String username) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        if (userMapper.getbabyUserMap(map) != null){
            return false;
        }
        return true;
    }

    /**
     * 用户注册
     * @param userAccount
     * @return
     * @throws Exception
     */
    @Override
    public boolean insertbabyUser(UserAccount userAccount) throws Exception {
        boolean flag = false;
        if(userMapper.insertbabyUser(userAccount) != 1){
             throw new Exception("注册失败");
        } else {
            flag = true;
        }
        return flag;
    }
}
