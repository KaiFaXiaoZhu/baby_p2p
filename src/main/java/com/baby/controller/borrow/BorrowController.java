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

    //查询标的信息
    @RequestMapping(value = "/query")
    @ResponseBody
    public Object getBorrowList(Borrow borrow){
        Map<String,Object> result = new HashMap<>();
        try{
            System.err.println("BorrowState=="+borrow.getBorrowState());

            List<Borrow> listData =borrowService.getBorrowList(borrow);
            if (!StringUtil.isEmpty(listData)) {
                BorrowPage<Borrow> borrowPage=new BorrowPage<Borrow>();
                borrowPage.setTotalPage(1);
                borrowPage.setCurrentPage(1);
                borrowPage.setPageSize(5);
                borrowPage.setListData(listData);
                result.put("data",borrowPage);
                result.put("code",200);
            }else{
                result.put("msg",300);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("msg",e.getMessage());
        }
        return result;
    }
}
