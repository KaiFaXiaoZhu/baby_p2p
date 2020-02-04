package com.baby.controller.bid;

import com.alibaba.fastjson.JSON;
import com.baby.common.AverageCapitalPlusInterestUtils;
import com.baby.common.IdUtils;
import com.baby.common.XianXiHouBeng;
import com.baby.pojo.*;
import com.baby.service.accountFlow.AccountFlowService;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.baby.service.user.UserService;
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
    @Resource
    private UserService userService;
    @Resource
    private AccountFlowService accountFlowService;

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
        int bidInterest=0;
        //int totalInterest=0;
        int flag=0;
        try{
            UserAccount user =(UserAccount) request.getSession().getAttribute("user");//获取登录用户的信息
            Borrow borrow=borrowService.getBorrowId(bid.getBorrowId());//查询borrow表
            Bid bid1=bidService.getBidByBidUserId(user.getId(),bid.getBorrowId());
            double yearRate =  borrow.getYearRate() * 0.01;//年利率
            if (borrow.getRepaymentType()==1) { //1.等额本息
                //等额本息投标计算总利息
                bidInterest = XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getInterestCount(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth()) , 100);
                //总回报金额
                //totalInterest= XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getInterestCount(borrow.getBorrowAmount()/100, yearRate, borrow.getRepaymentMonth()), 100);

            }else{//2.先息后本
                bidInterest = XianXiHouBeng.cheng(XianXiHouBeng.getXianXiHouBeng(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth()),100);
                //总回报金额
//                totalInterest= XianXiHouBeng.cheng(XianXiHouBeng.getXianXiHouBeng(borrow.getBorrowAmount()/100, yearRate, borrow.getRepaymentMonth()), 100);
            }
            //Bid信息封装
            bid.setBorrowTitle(borrow.getTitle());
            bid.setYearRate(borrow.getYearRate());
            bid.setBorrowState(borrow.getBorrowState());
            bid.setBidUserId(user.getId());
            bid.setBidUsername(user.getUsername());
            bid.setBidTime(new Date());
            bid.setCreateTime(new Date());
            if(null!=bid1) { //有此用户投标信息就进行修改，否则就执行增加
                bid.setId(bid1.getId());
                bid.setBidInterest(XianXiHouBeng.jia(bidInterest,bid1.getBidInterest()));
                bid.setBidAmount(Integer.parseInt(showBidAmount)* 100+bid1.getBidAmount());
                flag=bidService.modifyBid(bid);
            }else{
                bid.setId(IdUtils.getUUID());
                bid.setBidInterest(bidInterest);
                bid.setBidAmount(XianXiHouBeng.cheng(Integer.parseInt(showBidAmount),100));
                flag=bidService.addBid(bid);
            }
            if(flag==1) { //增加或修改成功就对borrow表进行更新

                //用户钱包扣除投标金额
                UserWallet userWallet=userService.selectBabyUserwallet(user.getId());
                userWallet.setAvailableAmount(XianXiHouBeng.jian(userWallet.getAvailableAmount(),Integer.parseInt(showBidAmount)*100));
                userWallet.setFreezeAmount(XianXiHouBeng.jia(userWallet.getFreezeAmount(),Integer.parseInt(showBidAmount)*100));
                int money=userService.updateBabyUserwallt(userWallet);

                //添加账户流水
                AccountFlow accountFlow=new AccountFlow();
                accountFlow.setAccountId(user.getId());
                accountFlow.setAmount(XianXiHouBeng.cheng(Integer.parseInt(showBidAmount),100));
                accountFlow.setFlowType(20);
                accountFlow.setAvailableAmount(userWallet.getAvailableAmount());
                accountFlow.setFreezeAmount(userWallet.getFreezeAmount());
                accountFlow.setRemark("投标【"+borrow.getTitle()+"】, 冻结账户可用余额："+XianXiHouBeng.cheng(Integer.parseInt(showBidAmount),100)+"元");
                accountFlow.setCreateTime(new Date());
                int num=accountFlowService.insterRepaymentFlow(accountFlow);


                List<Bid> bidList = bidService.getByBorrowId(bid);
                //borrow信息封装
                borrow.setBorrowState(borrow.getBorrowAmount()==bidList.stream().mapToInt(Bid::getBidAmount).sum()?30:20);
                borrow.setBidNum(bidList.size());
                borrow.setCurrentBidAmount(bidList.stream().mapToInt(Bid::getBidAmount).sum());
                borrow.setCurrentBidInterest(bidList.stream().mapToInt(Bid::getBidInterest).sum());
                borrow.setTotalInterest(bidList.stream().mapToInt(Bid::getBidInterest).sum());
                if (borrowService.modifyBorrow(borrow) == 1) {
                    result.put("data", bidList);
                    result.put("code", 200);
                }
            }
        }catch (Exception e){
            result.put("msg",e.getMessage());
        }
        return result;
    }

}
