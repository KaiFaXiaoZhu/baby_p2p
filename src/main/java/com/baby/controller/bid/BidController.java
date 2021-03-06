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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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
            System.out.println("findByBorrowId系统异常");
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
                bidInterest = XianXiHouBeng.cheng(
                        AverageCapitalPlusInterestUtils.Zong(Integer.parseInt(showBidAmount), AverageCapitalPlusInterestUtils.
                                getPrincipalInterestCount(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth()))+"" , "100");
                //总回报金额
                //totalInterest= XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getInterestCount(borrow.getBorrowAmount()/100, yearRate, borrow.getRepaymentMonth()), 100);

            }else{//2.先息后本
                bidInterest = XianXiHouBeng.cheng(XianXiHouBeng.getXianXiHouBeng(Integer.parseInt(showBidAmount), yearRate, borrow.getRepaymentMonth())+"","100");
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
            if(null!=bid1) { //有此用户投标信息就进行修改，否则就执行增加
                bid.setId(bid1.getId());
                bid.setBidInterest(XianXiHouBeng.jia(bidInterest+"",bid1.getBidInterest().toString()));
                bid.setBidAmount(Integer.parseInt(showBidAmount)* 100+bid1.getBidAmount());
                flag=bidService.modifyBid(bid);
            }else{
                bid.setId(IdUtils.getUUID());
                bid.setBidInterest(bidInterest);
                bid.setBidAmount(XianXiHouBeng.cheng(Integer.parseInt(showBidAmount)+"","100"));
                bid.setCreateTime(new Date());
                flag=bidService.addBid(bid);
            }
            if(flag==1) { //增加或修改成功就对borrow表进行更新

                //用户钱包扣除投标金额
                UserWallet userWallet=userService.selectBabyUserwallet(user.getId());
                userWallet.setAvailableAmount(XianXiHouBeng.jian(userWallet.getAvailableAmount().toString(),((Integer.parseInt(showBidAmount)*100)+"")));
                userWallet.setFreezeAmount(XianXiHouBeng.jia(userWallet.getFreezeAmount().toString(),((Integer.parseInt(showBidAmount)*100)+"")));
                int money=userService.updateBabyUserwallt(userWallet);

                //添加账户流水
                AccountFlow accountFlow=new AccountFlow(null,user.getId(),XianXiHouBeng.cheng(Integer.parseInt(showBidAmount)+"","100"),20,userWallet.getAvailableAmount(),
                        userWallet.getFreezeAmount(),"投标【"+borrow.getTitle()+"】, 冻结账户可用余额："+Integer.parseInt(showBidAmount)+"元",new Date());
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
            System.out.println("addBid系统异常");
        }
        return result;
    }
    /***
     * 30秒执行一次  流标
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void flushBorrowStatus() {
        System.out.println("进入流标任务调度器");
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("borrowState",20);
            List<Borrow> borrowList=borrowService.getBorrowListLimit(result);
            for (Borrow borrow:borrowList) {
                boolean flag=compare(borrow.getBidDeadline(),new Date());
                if(flag){
                    borrow.setBorrowState(21);
                    int borrowModify=borrowService.modifyBorrow(borrow);

                    //退款
                    Bid bid=new Bid();
                    bid.setBorrowId(borrow.getId());
                    bidService.TuiK(bidService.getByBorrowId(bid),borrow);


                    System.out.println(borrowModify!=0?"流标更新成功":"流标更新失败");
                }else{
                    System.out.println("没有流标");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("flushBorrowStatus系统异常");
        }
    }

    /**
     * 日期比较
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public boolean compare(Date time1,Date time2) throws ParseException {
        Date a = time1;
        Date b = time2;
        //Date类的一个方法，如果a早于b返回true，否则返回false
        if (a.before(b))
            return true;
        else
            return false;
    }

}
