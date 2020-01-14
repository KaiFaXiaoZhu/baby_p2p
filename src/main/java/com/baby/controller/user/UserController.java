package com.baby.controller.user;

import com.baby.common.IdUtils;
import com.baby.common.MD5Utils;
import com.baby.common.StringUtil;
import com.baby.pojo.UserAccount;
import com.baby.service.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.DateUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@Controller
public class UserController {
    @Resource
    UserService userService;

    /**
     * 用户名唯一性查询
     * @param username
     * @return
     */
    @RequestMapping("/checkUsername")
    @ResponseBody
    public String checkusername(String username){
        try {
            if(userService.getbabyUserByname(username)){
                   return "true";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    /**
     * 账户注册
     * @param userAccount
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> Login(UserAccount userAccount){
        Map<String,Object> result = new HashMap<>();
        if (!StringUtil.isEmpty(userAccount)){
            try {
                String password = MD5Utils.getMD5Str(userAccount.getPassword());
                //信息封装
                userAccount.setPassword(password);
                userAccount.setId(IdUtils.getUUID());
                userAccount.setAccountStatus(1);
                userAccount.setAccountType(1);
                userAccount.setCreateTime(new Date());
                //添加判断
                if(userService.insertbabyUser(userAccount)){
                    result.put("code",200);
                } else {
                    result.put("code",500);
                    result.put("msg","注册失败！");
                }
            } catch (Exception e) {
                result.put("code",500);
                result.put("msg","系统异常！");
                e.printStackTrace();
            }
        } else {
            result.put("code",500);
            result.put("msg","注册信息错误！");
        }
        return result;
    }
}
