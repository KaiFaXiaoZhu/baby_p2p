package com.baby.controller.bid;

import com.alibaba.fastjson.JSON;
import com.baby.common.AverageCapitalPlusInterestUtils;
import com.baby.common.IdUtils;
import com.baby.pojo.Bid;
import com.baby.pojo.Borrow;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/finance/bid")
public class BidController {
    @Resource
    private BidService bidService;
    @Resource
    private BorrowService borrowService;

    //根据borrowId查询借款信息
    @PostMapping(value = "/getByBorrowId/{borrowId}")
    @ResponseBody
    public Object findByBorrowId(@PathVariable String borrowId){
        Map<String,Object> result = new HashMap<>();
        try{
            Bid bid=new Bid();
            bid.setBorrowId(borrowId);
            List<Bid> bidList=bidService.getByBorrowId(bid);
            result.put("data",bidList);
            result.put("code",200);
        }catch (Exception e){
            result.put("msg",e.getMessage());
        }
        return result;
    }

    //投标
    @PostMapping(value = "/add")
    //@ResponseBody
    public Object addBid(Bid bid,String showBidAmount){
        Borrow borrow=borrowService.getBorrowId(bid.getBorrowId());
        double yearRate=borrow.getYearRate()*0.01;
        double num=AverageCapitalPlusInterestUtils.getInterestCount(Double.parseDouble(showBidAmount),yearRate,borrow.getRepaymentMonth());
        try{
            bid.setId(IdUtils.getUUID());
            bid.setBorrowTitle(borrow.getTitle());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("num=="+num);
        return null;//JSON.toJSONString(num);
    }

}
