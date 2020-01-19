package com.baby.controller.bid;

import com.alibaba.fastjson.JSON;
import com.baby.common.AverageCapitalPlusInterestUtils;
import com.baby.common.IdUtils;
import com.baby.common.XianXiHouBeng;
import com.baby.pojo.Bid;
import com.baby.pojo.Borrow;
import com.baby.pojo.UserAccount;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.Date;
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
    public Object addBid(HttpSession session,Bid bid, String showBidAmount){
        double num=0;
        Integer bidInterest=null;
        try{
            UserAccount user =(UserAccount) session.getAttribute("user");
            Borrow borrow=borrowService.getBorrowId(bid.getBorrowId());//查询borrow表
            double yearRate = borrow.getYearRate() * 0.01;//年利率
            if (borrow.getRepaymentType()==1) { //1.等额本息
                //等额本息投标计算利息
                num = AverageCapitalPlusInterestUtils.getInterestCount(Double.parseDouble(showBidAmount), yearRate, borrow.getRepaymentMonth()) * 100;
                bidInterest = (int) num;
            }else{//2.先息后本
                num = XianXiHouBeng.getXianXiHouBeng(Double.parseDouble(showBidAmount), yearRate, borrow.getRepaymentMonth())* 100;
                bidInterest = (int) num;
            }
            //信息封装
            bid.setId(IdUtils.getUUID());
            bid.setBorrowTitle(borrow.getTitle());
            bid.setBidAmount(Integer.parseInt(showBidAmount));
            bid.setBidInterest(bidInterest);
            bid.setYearRate(borrow.getYearRate());
            bid.setBorrowState(borrow.getBorrowState());
            bid.setBidUserId(user.getId());
            bid.setBidUsername(user.getUsername());
            bid.setCreateTime(new Date());


        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
