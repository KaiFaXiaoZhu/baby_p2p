package com.baby.controller.loan;

import com.alibaba.fastjson.JSON;
import com.baby.common.AverageCapitalPlusInterestUtils;
import com.baby.common.IdUtils;
import com.baby.common.XianXiHouBeng;
import com.baby.controller.bid.BidController;
import com.baby.pojo.*;
import com.baby.service.accountFlow.AccountFlowService;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.baby.service.repayment.RepaymentService;
import com.baby.service.repaymentDetail.RepaymentDetailService;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;


/**
 * 满标审核
 */
@Controller
@RequestMapping(value = "/finance/loan")
public class LoanController {
    @Resource
    private BidService bidService;
    @Resource
    private BorrowService borrowService;
    @Resource
    private UserService userService;
    @Resource
    private AccountFlowService accountFlowService;
    @Resource
    private RepaymentService repaymentService;
    @Resource
    private RepaymentDetailService repaymentDetailService;

    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(Integer borrowState,String borrowId){
        Map<String,Object> result = new HashMap<>();
        Bid bid=new Bid();
        try{
            if(borrowState==31){// 放款审核拒绝

                bid.setBorrowId(borrowId);

                //修改borrow状态
                Borrow borrow=borrowService.getBorrowId(borrowId);
                borrow.setBorrowState(borrowState);
                int borrowModify=borrowService.modifyBorrow(borrow);

                //退款
                bidService.TuiK(bidService.getByBorrowId(bid),borrow);

                result.put("code", 200);
            }else{// 放款审核通过

                bid.setBorrowId(borrowId);
                List<Bid> bidList=bidService.getByBorrowId(bid);

                //修改borrow状态
                Borrow borrow=borrowService.getBorrowId(borrowId);
                borrow.setBorrowState(40);
                int borrowModify=borrowService.modifyBorrow(borrow);

                //审核放款
                bid.setBorrowId(borrowId);
                JieK(bidList,borrow);

                Integer totalAmount=0;               //本期还款总金额(单位：分)
                Integer principal=0;                  //本期还款本金(单位：分)
                Integer interest=0;                   //本期还款利息(单位：分)

                borrow.setBorrowAmount(XianXiHouBeng.chu(borrow.getBorrowAmount().toString(),"100"));
                for (int i=1;i<=borrow.getRepaymentMonth();i++){

                    if (borrow.getRepaymentType()==1){//等额本息
                        totalAmount=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(borrow.getBorrowAmount(), borrow.getYearRate()*0.01, borrow.getRepaymentMonth())+"","100");
                        principal=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthPrincipal(borrow.getBorrowAmount(), borrow.getYearRate()*0.01, borrow.getRepaymentMonth()).get(i).doubleValue()+"","100");
                        interest=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthInterest(borrow.getBorrowAmount(), borrow.getYearRate()*0.01, borrow.getRepaymentMonth()).get(i).doubleValue()+"","100");
                    }else{//先息后本
                        totalAmount=XianXiHouBeng.cheng(XianXiHouBeng.monthlyRepayment(new BigDecimal(borrow.getBorrowAmount()),new BigDecimal(borrow.getYearRate()*0.01),borrow.getRepaymentMonth(),i)+"","100");
                        principal=i==12?XianXiHouBeng.cheng(borrow.getBorrowAmount().toString(),"100"):0;
                        interest=XianXiHouBeng.cheng(XianXiHouBeng.getYueLiX(borrow.getBorrowAmount(),borrow.getYearRate()*0.01,borrow.getRepaymentMonth())+"","100");
                    }

                    Calendar curr = Calendar.getInstance();
                    curr.set(Calendar.MONTH,curr.get(Calendar.MONTH)+borrow.getRepaymentMonth());

                    Repayment repayment=new Repayment(IdUtils.getUUID(),borrow.getId(),borrow.getBorrowUserId(),borrow.getTitle(),borrow.getBidDeadline(),curr.getTime(),
                            totalAmount,principal,interest, i,2,borrow.getBorrowType(),1,new Date());
                    int repaymentAdd=repaymentService.addRepayment(repayment);
                }

                List<Repayment> repaymentList=repaymentService.getByBorrowId(borrowId);
                for (Repayment repayment:repaymentList){
                    for (Bid bid1:bidList){
                        Integer bidAmount=XianXiHouBeng.chu(bid1.getBidAmount()+"","100");
                        RepaymentDetail repaymentDetail=new RepaymentDetail();
                        if (borrow.getRepaymentType()==1){//等额本息
                            totalAmount=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(bidAmount, bid1.getYearRate()*0.01, borrow.getRepaymentMonth())+"","100");
                            principal=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthPrincipal(bidAmount, bid1.getYearRate()*0.01, borrow.getRepaymentMonth()).get(repayment.getPeriod()).doubleValue()+"","100");
                            interest=XianXiHouBeng.cheng(AverageCapitalPlusInterestUtils.getPerMonthInterest(bidAmount, bid1.getYearRate()*0.01, borrow.getRepaymentMonth()).get(repayment.getPeriod()).doubleValue()+"","100");
                            repaymentDetail.setRepaymentType(borrow.getRepaymentType());
                        }else{//先息后本
                            totalAmount=XianXiHouBeng.cheng(XianXiHouBeng.monthlyRepayment(new BigDecimal(bid1.getBidAmount()),new BigDecimal(bid1.getYearRate()*0.01),borrow.getBorrowAmount(),repayment.getPeriod())+"","100");
                            principal=repayment.getPeriod()==12?bid1.getBidAmount():0;
                            interest=XianXiHouBeng.cheng(XianXiHouBeng.getYueLiX(bid1.getBidAmount(),bid1.getYearRate()*0.01,borrow.getRepaymentMonth())+"","100");
                            repaymentDetail.setRepaymentType(borrow.getRepaymentType());
                        }
                        //封装
                        repaymentDetail.setId(IdUtils.getUUID());
                        repaymentDetail.setBidId(bid1.getId());
                        repaymentDetail.setBorrowId(bid1.getBorrowId());
                        repaymentDetail.setRepaymentId(repayment.getId());
                        repaymentDetail.setBorrowUserId(borrow.getBorrowUserId());
                        repaymentDetail.setBidUserId(bid1.getBidUserId());
                        repaymentDetail.setBorrowTitle(borrow.getTitle());
                        repaymentDetail.setTotalAmount(totalAmount);
                        repaymentDetail.setPrincipal(principal);
                        repaymentDetail.setInterest(interest);
                        repaymentDetail.setPeriod(repayment.getPeriod());
                        repaymentDetail.setDeadline(repayment.getDeadline());
                        repaymentDetail.setRepaymentTime(repayment.getRepaymentTime());
                        repaymentDetail.setCreateTime(new Date());
                        int RepaymentDetailAdd=repaymentDetailService.addRepaymentDetail(repaymentDetail);
                    }
                }

                result.put("code", 200);
            }
        }catch (Exception e){
            result.put("msg", e.getMessage());
        }
        return result;
    }

    //审核扣除投标冻结金额
    public void JieK(List<Bid> bidList, Borrow borrow){
        int num=0;
        UserWallet userWallet=null;
        AccountFlow accountFlow=null;
        try{
            //借款总金额增加至借款人钱包余额
            userWallet=userService.selectBabyUserwallet(borrow.getBorrowUserId());
            userWallet.setAvailableAmount(XianXiHouBeng.jia(userWallet.getAvailableAmount().toString(),borrow.getBorrowAmount().toString()));
            num=userService.updateBabyUserwallt(userWallet);

            //借款成功添加账户流水
            accountFlow=new AccountFlow(null,borrow.getBorrowUserId(),borrow.getBorrowAmount(),10,userWallet.getAvailableAmount(),userWallet.getFreezeAmount(),
                    "借款【"+borrow.getTitle()+"】成功，收到借款金额"+XianXiHouBeng.chu(borrow.getBorrowAmount().toString(),"100")+"元",new Date());
            num=accountFlowService.insterRepaymentFlow(accountFlow);
            for (Bid bid:bidList){
                //修改borrowState
                bid.setBorrowState(borrow.getBorrowState());
                num=bidService.modifyBid(bid);

                //扣除投标冻结金额
                userWallet=userService.selectBabyUserwallet(bid.getBidUserId());//用户钱包
                //userWallet.setAvailableAmount(XianXiHouBeng.jia(bid.getBidAmount().toString(),userWallet.getAvailableAmount().toString()));
                userWallet.setFreezeAmount(XianXiHouBeng.jian(userWallet.getFreezeAmount().toString(),bid.getBidAmount().toString()));
                num=userService.updateBabyUserwallt(userWallet);

                //添加账户流水
                accountFlow=new AccountFlow(null,bid.getBidUserId(),bid.getBidAmount(),22,userWallet.getAvailableAmount(),userWallet.getFreezeAmount(),
                        "投标【"+borrow.getTitle()+"】成功，扣除投标冻结金额："+XianXiHouBeng.chu(bid.getBidAmount().toString(),"100")+"元",new Date());
                num=accountFlowService.insterRepaymentFlow(accountFlow);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("JieK系统异常");
        }
    }

//    public static void main(String[] args) {
//
//        double perMonthPrincipalInterest = AverageCapitalPlusInterestUtils.getPerMonthPrincipalInterest(borrow.getBorrowAmount(), borrow.getYearRate()*0.01, borrow.getRepaymentMonth());
//        System.out.println("等额本息---每月还款本息：" + perMonthPrincipalInterest);
//        Map<Integer, BigDecimal> mapInterest = AverageCapitalPlusInterestUtils.getPerMonthInterest(borrow.getBorrowAmount(), borrow.getYearRate()*0.01, borrow.getRepaymentMonth());
//        System.out.println("等额本息---每月还款利息：" + mapInterest);
//        Map<Integer, BigDecimal> mapPrincipal = AverageCapitalPlusInterestUtils.getPerMonthPrincipal(5000, 10*0.01,12);
//        System.out.println("等额本息---每月还款本金：" + mapPrincipal);
//        System.out.println(mapPrincipal.get(1).doubleValue());
//    }
}
