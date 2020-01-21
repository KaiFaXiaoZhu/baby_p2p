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
import javax.servlet.http.HttpServletRequest;
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
    @ResponseBody
    public Object addBid(HttpServletRequest request, Bid bid, String showBidAmount){
        Map<String,Object> result = new HashMap<>();
        double num=0;
        double totalInterest1=0;
        try{
            UserAccount user =(UserAccount) request.getSession().getAttribute("user");
            Borrow borrow=borrowService.getBorrowId(bid.getBorrowId());//查询borrow表
            double yearRate = borrow.getYearRate() * 0.01;//年利率
            if (borrow.getRepaymentType()==1) { //1.等额本息
                //等额本息投标计算总利息
                num = AverageCapitalPlusInterestUtils.getInterestCount(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth()) * 100;
                //总回报金额
                totalInterest1= AverageCapitalPlusInterestUtils.getInterestCount(borrow.getBorrowAmount()/100, yearRate, borrow.getRepaymentMonth())* 100;

            }else{//2.先息后本
                num = XianXiHouBeng.getXianXiHouBeng(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth())*100;
                //总回报金额
                totalInterest1= XianXiHouBeng.getXianXiHouBeng(borrow.getBorrowAmount()/100, yearRate, borrow.getRepaymentMonth())* 100;
            }
            Integer bidInterest = (int) num;
            Integer totalInterest=(int)totalInterest1;
            //Bid信息封装
            bid.setId(IdUtils.getUUID());
            bid.setBorrowTitle(borrow.getTitle());
            bid.setBidAmount(Integer.parseInt(showBidAmount)*100);
            bid.setBidInterest(bidInterest);
            bid.setYearRate(borrow.getYearRate());
            bid.setBorrowState(borrow.getBorrowState());
            bid.setBidUserId(user.getId());
            bid.setBidUsername(user.getUsername());
            bid.setBidTime(new Date());
            bid.setCreateTime(new Date());
            if(bidService.addBid(bid)==1){
                List<Bid> bidList=bidService.getByBorrowId(bid);
                //borrow信息封装
                borrow.setBidNum(bidList.size());
                borrow.setCurrentBidAmount(bidList.stream().mapToInt(Bid::getBidAmount).sum());
                borrow.setCurrentBidInterest(bidList.stream().mapToInt(Bid::getBidInterest).sum());
                borrow.setTotalInterest(totalInterest);
                if (borrowService.modifyBorrow(borrow)==1) {
                    result.put("data",bidList);
                    result.put("code",200);
                }
            }
        }catch (Exception e){
            result.put("msg",e.getMessage());
        }
        return result;
    }

}
