package com.baby.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baby.common.BorrowPage;
import com.baby.common.IdUtils;
import com.baby.common.StringUtil;
import com.baby.pojo.*;
import com.baby.service.user.UserService;
import com.google.common.io.Files;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    public Map<String,Object> Userlogin(String username, String password, HttpServletRequest request, HttpSession session){
        Map<String,Object> result = new HashMap<>();
        UserAccount userAccount = null;
        try {
            userAccount = userService.loginUser(username,password,request);
            result.put("code",200);
            result.put("data",userAccount);
            session.setAttribute("user",userAccount);
        } catch (Exception e) {
            result.put("code",500);
            e.printStackTrace();
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
                result.put("msg","请完善个人资料！");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 头像上传
     * @param file
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/userinfo/uploadAvatar")
    @ResponseBody
    public Map<String,Object> uploadAvatarsave(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String,Object> result = new HashMap<>();
        try {
            //获取文件名
            String fileName  = file.getOriginalFilename();
            // 获取项目的路径 + 拼接得到文件要保存的位置
            String filePath = System.getProperties().getProperty("user.dir") + "\\src\\main\\resources\\static\\avatar\\" + fileName;
            System.out.println(filePath);
            // 创建一个文件的对象
            File file1 = new File(filePath);
            // 创建父文件夹
            Files.createParentDirs(file1);
            // 把上传的文件复制到文件对象中
            file.transferTo(file1);
            result.put("code",200);
            result.put("data",fileName);
        } catch (Exception e){
            result.put("code",500);
            result.put("msg","上传失败");
        }
        return result;
    }

    /**
     * 用户信息的修改
     * @param userInfo
     * @param request
     * @return
     */
    @PostMapping("/userinfo/update")
    @ResponseBody
    public Map<String,Object> modifUserBabyUserInfo(UserInfo userInfo,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        if(!StringUtil.isEmpty(userInfo)){
            try {
                userInfo.setAccountId(((UserAccount)request.getSession().getAttribute("user")).getId());
                if (userService.updateBabyUserInfo(userInfo)){
                    result.put("code",200);
                } else {
                    result.put("code",500);
                    result.put("msg","修改失败!");
                }
            } catch (Exception e) {
                result.put("code",500);
                result.put("msg","修改失败!");
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 银行卡信息的获取
     * @param id
     * @return
     */
    @PostMapping("/bankcard/get/{id}")
    @ResponseBody
    public Map<String,Object> getBankCard(@PathVariable String id){
        Map<String,Object> result = new HashMap<>();
        BankCard bankCard = null;
        try {
            bankCard = userService.selectBabyBankCard(id);
            if(bankCard != null){
                result.put("code","200");
                result.put("data",bankCard);
            } else {
                result.put("code","404");
            }
        } catch (Exception e) {
            result.put("code","500");
            result.put("msg","系统异常！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 银行卡绑定
     * @param bankCard
     * @return
     */
    @PostMapping("/bankcard/add")
    @ResponseBody
    public Map<String,Object> addbankcard(BankCard bankCard){
        Map<String,Object> result = new HashMap<>();
        try {
            if(userService.insertBabyBankCard(bankCard)){
                result.put("code",200);
            } else {
                result.put("code",500);
                result.put("msg","资料错误！");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常！");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 余额提现
     * @return
     */
    @PostMapping("/withdraw/add")
    @ResponseBody
    public Map<String,Object> addwithdraw(Withdraw withdraw,HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        String user_id = ((UserAccount)request.getSession().getAttribute("user")).getId();
        //获取用户信息,得到开户人姓名
        UserInfo userInfo = userService.selectBabyUserInfo(user_id);
        //提现记录信息封装
        withdraw.setId(IdUtils.getUUID());
        withdraw.setRealname(userInfo.getRealname());
        withdraw.setUserId(user_id);
        withdraw.setCreateTime(new Date());
        //获取账户钱包
        UserWallet userWallet = userService.selectBabyUserwallet(user_id);
        if(userService.insertBabyWithdraw(withdraw,userWallet)){
            result.put("code",200);
        } else {
            result.put("code",500);
            result.put("msg","系统异常");
        }
        return result;
    }

    /**
     * 加载’账户流水‘
     * @return
     */
    @PostMapping("/accountflow/query")
    @ResponseBody
    public Map<String,Object> queryaccountflow(@RequestParam(required = false) String beginDate,
                                               @RequestParam(required = false) String endDate,
                                               @RequestParam(required = false) Integer currentPage,
                                               String userId){
        Map<String,Object> result = new HashMap<>();
        Integer num = userService.selectBabyaccountFlowCount(beginDate,endDate,userId);
        if(!StringUtil.isEmpty(num)){
            BorrowPage<AccountFlow> borrowPage = new BorrowPage<>();
            borrowPage.setPageSize(10);
            borrowPage.setCurrentPage(StringUtil.isEmpty(currentPage)?1:currentPage);
            //计算当前页
            currentPage = (borrowPage.getCurrentPage()-1) * borrowPage.getPageSize();
            List<AccountFlow> listData = userService.selectBabyaccountFlow(beginDate,endDate,userId,currentPage,borrowPage.getPageSize());
            borrowPage.setTotalPage((num +borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data",borrowPage);
            result.put("code",200);
        }
        return result;
    }

}
