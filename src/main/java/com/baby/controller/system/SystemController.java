package com.baby.controller.system;

import com.baby.common.BorrowPage;
import com.baby.common.StringUtil;
import com.baby.pojo.*;
import com.baby.service.system.SystemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {
    @Resource
    SystemService systemService;

    /**
     * 加载数据字典
     * @return
     */
    @GetMapping("/dictionaryItem/getAll")
    @ResponseBody
    public Map<String,Object> getDictionaryItem(){
        Map<String,Object> result = new HashMap<>();
        List<SystemDictionaryItem> systemDictionaryItemList = null;
        try {
            systemDictionaryItemList = systemService.selectDictionaryItem(null);
            if(systemDictionaryItemList.size() > 0){
                result.put("code",200);
                result.put("data",systemDictionaryItemList);
            } else {
                result.put("code",500);
                result.put("msg","加载字典失败");
            }
        } catch (Exception e) {
            result.put("code",500);
            result.put("msg","系统异常");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 账户充值
     * @param recharge
     * @param request
     * @return
     */
    @PostMapping("/recharge/add")
    @ResponseBody
    public Map<String,Object> addrecharge(Recharge recharge, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        UserAccount userAccount = (UserAccount)request.getSession().getAttribute("user");
        if(StringUtil.isEmpty(recharge.getAmount()) || recharge.getAmount() == 0){
            result.put("code",500);
            result.put("msg","请输入正确的金额");
        } else {
            if(systemService.insertRecharge(recharge,userAccount)){
                result.put("code",200);
            } else {
                result.put("code",500);
                result.put("msg","充值失败");
            }
        }

        return result;
    }

    /**
     * 加载'充值记录'数据
     * @param beginDate
     * @param endDate
     * @param state
     * @param userId
     * @param currentPage
     * @param request
     * @return
     */
    @PostMapping("/recharge/query")
    @ResponseBody
    public Map<String,Object> queryRecharge(@RequestParam(required = false) String beginDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(required = false) Integer state,
                                            String userId,
                                            @RequestParam(required = false) Integer currentPage, HttpServletRequest request){
        Map<String,Object> result = new HashMap<>();
        //方便于动态sql的判断
        if(!StringUtil.isEmpty(state)){
            state = state == -1 ? null:state;
        }
        Integer num =  systemService.selectRechargeCount(beginDate,endDate,state,userId);  //充值记录数目
        if(!StringUtil.isEmpty(num)){
            BorrowPage<Recharge> borrowPage = new BorrowPage<>();
            borrowPage.setPageSize(10);
            borrowPage.setCurrentPage(StringUtil.isEmpty(currentPage)?1:currentPage);
            //计算后的当前页码
            currentPage = (borrowPage.getCurrentPage()-1)*borrowPage.getPageSize();
            //查询分页后的集合
            List<Recharge> listData = systemService.selectRecharge(beginDate,endDate,state,userId,currentPage,borrowPage.getPageSize());
            borrowPage.setTotalPage((num +borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data",borrowPage);
            result.put("code",200);
        }
        return result;
    }

    /**
     * 加载'登录记录'数据
     * @return
     */
    @PostMapping("/loginlog/query")
    @ResponseBody
    public Map<String,Object> queryloginlog(@RequestParam(required = false)String beginDate,
                                            @RequestParam(required = false)String endDate,
                                            @RequestParam(required = false)Integer loginResult,
                                            @RequestParam(required = false)String username,
                                            @RequestParam(required = false)Integer currentPage){
        Map<String,Object> result = new HashMap<>();
        //方便于动态sql的判断
        if(!StringUtil.isEmpty(loginResult)){
            loginResult = loginResult == -1 ? null:loginResult;
        }
        //记录数量
        Integer num = systemService.selectLoginLogCount(beginDate,endDate,loginResult,username);
        if(!StringUtil.isEmpty(num)){
            BorrowPage<LoginLog> borrowPage = new BorrowPage<>();
            borrowPage.setPageSize(10);
            borrowPage.setCurrentPage(StringUtil.isEmpty(currentPage)?1:currentPage);
            //计算后的当前页码
            currentPage = (borrowPage.getCurrentPage()-1)*borrowPage.getPageSize();
            List<LoginLog> listData = systemService.selectLoginLog(beginDate,endDate,loginResult,username,currentPage,borrowPage.getPageSize());
            borrowPage.setTotalPage((num +borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data",borrowPage);
            result.put("code",200);
        }
        return result;
    }

    /**
     * 加载'数据字典项'数据
     * @return
     */
    @PostMapping("/dictionary/getAll")
    @ResponseBody
    public Map<String,Object> getAlldictionary(){
        Map<String,Object> result = new HashMap<>();
        List<SystemDictionary> systemDictionaries = systemService.selectDictionary();
        if(!StringUtil.isEmpty(systemDictionaries)){
            result.put("code",200);
            result.put("data",systemDictionaries);
        } else {
            result.put("code",500);
            result.put("msg","系统异常");
        }
        return result;
    }

    /**
     *加载'数据字典'数据
     * @param keyword
     * @param currentPage
     * @param parentId
     * @return
     */
    @PostMapping("/dictionaryItem/query")
    @ResponseBody
    public Map<String,Object> querydictionary(@RequestParam(required = false) String keyword,
                                              @RequestParam(required = false) Integer currentPage,
                                              @RequestParam(required = false) Integer parentId){
        Map<String,Object> result = new HashMap<>();  //数据传输Map
        Map<String,Object> map = new HashMap<>(); //条件Map
        map.put("parentId",parentId);
        map.put("keyword",keyword);
        //获取记录数量
        Integer num = systemService.selectDictionaryItemcount(map);

        if(!StringUtil.isEmpty(num)){
            BorrowPage<SystemDictionaryItem> borrowPage = new BorrowPage<>();
            borrowPage.setPageSize(5);
            borrowPage.setCurrentPage(StringUtil.isEmpty(currentPage)?1:currentPage);
            //计算后的当前页码
            currentPage = (borrowPage.getCurrentPage()-1)*borrowPage.getPageSize();
            map.put("current",currentPage);
            map.put("size",borrowPage.getPageSize());
            List<SystemDictionaryItem> listData = systemService.selectDictionaryItem(map);
            borrowPage.setTotalPage((num +borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data",borrowPage);
            result.put("code",200);
        }
        return result;
    }

    /**
     * 添加字典
     * @param parentId
     * @param value
     * @param orderNo
     * @return
     */
    @PostMapping("/dictionaryItem/add")
    @ResponseBody
    public Map<String,Object> adddictionaryItem(Integer parentId,String value,Integer orderNo){
        Map<String,Object> result = new HashMap<>();
        SystemDictionaryItem systemDictionaryItem = new SystemDictionaryItem();
        systemDictionaryItem.setParentId(parentId);
        systemDictionaryItem.setValue(value);
        systemDictionaryItem.setOrderNo(orderNo);
        systemDictionaryItem.setCreateTime(new Date());
        if(systemService.insertDictionaryItem(systemDictionaryItem)){
            result.put("code",200);
        } else {
            result.put("code",500);
            result.put("msg","添加失败");
        }
        return result;
    }

    /**
     * 字典修改
     * @param id
     * @param value
     * @param orderNo
     * @return
     */
    @PostMapping("/dictionaryItem/update")
    @ResponseBody
    public Map<String,Object> updatedictionaryItem(Integer id,String value,Integer orderNo){
        Map<String,Object> result = new HashMap<>();
        SystemDictionaryItem systemDictionaryItem = new SystemDictionaryItem();
        systemDictionaryItem.setId(id);
        systemDictionaryItem.setValue(value);
        systemDictionaryItem.setOrderNo(orderNo);
        systemDictionaryItem.setCreateTime(new Date());
        if(systemService.updatedictionaryItem(systemDictionaryItem)){
            result.put("code",200);
        } else {
            result.put("code",500);
            result.put("msg","修改失败");
        }
        return result;
    }




}
