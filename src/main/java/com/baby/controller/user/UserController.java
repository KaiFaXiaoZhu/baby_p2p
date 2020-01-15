package com.baby.controller.user;

import com.baby.common.IdUtils;
import com.baby.common.StringUtil;
import com.baby.pojo.UserAccount;
import com.baby.pojo.UserInfo;
import com.baby.pojo.UserWallet;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @PostMapping("/checkUsername")
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
    @PostMapping("/register")
    @ResponseBody
    public Map<String,Object> Login(UserAccount userAccount){
        Map<String,Object> result = new HashMap<>();
        if (!StringUtil.isEmpty(userAccount)){
            try {
                //信息封装
                userAccount.setId(IdUtils.getUUID());
                userAccount.setPassword(DigestUtils.md5DigestAsHex(userAccount.getPassword().getBytes()));
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

    /**
     * 登录
     * @param username
     * @param password
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> Userlogin(String username, String password, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        UserAccount userAccount = null;
        try {
            userAccount = userService.loginUser(username,password,request);
        } catch (Exception e) {
            result.put("code",500);
            e.printStackTrace();
        }
        if(userAccount != null){
            result.put("code",200);
            result.put("data",userAccount);
        } else {
            result.put("code",500);
        }
        return result;
    }

    /**
     * 账户钱包获取
     * @param id
     * @return
     */
    @PostMapping("/wallet/get/{id}")
    @ResponseBody
    public Map<String,Object> getUserWallet(@PathVariable("id")String id){
        Map<String,Object> result = new HashMap<>();
        try {
            UserWallet userWallet = userService.selectBabyUserwallet(id);
            if(userWallet != null){
                result.put("code",200);
                result.put("data",userWallet);
            } else {
                result.put("code",500);
                result.put("msg","获取失败");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户基本信息获取
     * @param id
     * @return
     */
    @GetMapping("/userinfo/get/{id}")
    @ResponseBody
    public Map<String,Object> getUserInfo(@PathVariable("id")String id){
        Map<String,Object> result = new HashMap<>();
        try {
            UserInfo userInfo = userService.selectBabyUserInfo(id);
            if(userInfo != null){
                result.put("code",200);
                result.put("data",userInfo);
            } else {
                result.put("code",500);
                result.put("msg","信息获取失败");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常");
            e.printStackTrace();
        }
        return result;
    }
}
