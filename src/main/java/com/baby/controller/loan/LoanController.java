package com.baby.controller.loan;

import com.baby.common.XianXiHouBeng;
import com.baby.controller.bid.BidController;
import com.baby.pojo.*;
import com.baby.service.accountFlow.AccountFlowService;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    private BidController bidController=new BidController();

    @RequestMapping(value = "/audit")
    @ResponseBody
    public Object audit(Integer borrowState,String borrowId){
        Map<String,Object> result = new HashMap<>();
        Bid bid=new Bid();
        try{
            if(borrowState==31){// 放款审核拒绝

                bid.setBorrowId(borrowId);
                List<Bid> bidList=bidService.getByBorrowId(bid);//根据borrowId查询bid表信息

                //修改borrow状态
                Borrow borrow=borrowService.getBorrowId(borrowId);
                borrow.setBorrowState(borrowState);
//                int borrowModify=borrowService.modifyBorrow(borrow);

                //退款
                bidController.TuiK(bidList,borrow);

                result.put("code", 200);
            }else{// 放款审核通过
                //修改borrow状态
                Borrow borrow=borrowService.getBorrowId(borrowId);
                borrow.setBorrowState(40);
                int borrowModify=borrowService.modifyBorrow(borrow);

                bid.setBorrowId(borrowId);
                JieK(bidService.getByBorrowId(bid),borrow);

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
                    "借款【"+borrow.getTitle()+"】成功，收到借款金额"+borrow.getBorrowAmount()+"元",new Date());
            num=accountFlowService.insterRepaymentFlow(accountFlow);
            for (Bid bid:bidList){
                //修改borrowState
                bid.setBorrowState(borrow.getBorrowState());
                num=bidService.modifyBid(bid);

                //扣除投标冻结金额
                userWallet=userService.selectBabyUserwallet(bid.getBidUserId());//用户钱包
                userWallet.setAvailableAmount(XianXiHouBeng.jia(bid.getBidAmount().toString(),userWallet.getAvailableAmount().toString()));
                userWallet.setFreezeAmount(XianXiHouBeng.jian(bid.getBidAmount().toString(),userWallet.getFreezeAmount().toString()));
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

}
