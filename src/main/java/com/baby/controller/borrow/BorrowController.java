package com.baby.controller.borrow;

import com.alibaba.fastjson.JSON;
import com.baby.common.BorrowPage;
import com.baby.common.Page;
import com.baby.common.StringUtil;
import com.baby.pojo.Borrow;
import com.baby.service.borrow.BorrowService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/finance/borrow")
public class BorrowController {

    @Resource
    private BorrowService borrowService;

    //查询借款信息
    @PostMapping(value = "/query")
    @ResponseBody
    public Object getBorrowList(String borrowStates,@RequestParam(required = false) Integer currentPage){
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> map = new HashMap<>();//查询条件
        int num=borrowService.getCountBorrow();//信息数量
        try{
            BorrowPage<Borrow> borrowPage=new BorrowPage<Borrow>();
            borrowPage.setPageSize(10);
            borrowPage.setCurrentPage(currentPage==null?1:currentPage);
            map.put("borrowStates",borrowStates);
            map.put("From",(borrowPage.getCurrentPage()-1)*borrowPage.getPageSize());
            map.put("pageSize",borrowPage.getPageSize());
            List<Borrow> listData = borrowService.getBorrowList(map);
            borrowPage.setTotalPage((num +borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);

            result.put("data",borrowPage);
            result.put("code",200);
        }catch (Exception e){
            e.printStackTrace();
            result.put("msg",e.getMessage());
        }
        return result;
    }


    //根据Id查询借款信息
    @GetMapping(value = "/get/{borrowId}")
    @ResponseBody
    public Object BorrowId(@PathVariable String borrowId){
        Map<String,Object> result = new HashMap<>();
        try{
            Borrow borrow1=borrowService.getBorrowId(borrowId);
            result.put("data",borrow1);
            result.put("code",200);
        }catch (Exception e){
            result.put("msg",e.getMessage());
        }
        return result;
    }
}
