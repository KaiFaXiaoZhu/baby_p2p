package com.baby.controller.loan;

import com.baby.common.XianXiHouBeng;
import com.baby.pojo.*;
import com.baby.service.accountFlow.AccountFlowService;
import com.baby.service.bid.BidService;
import com.baby.service.borrow.BorrowService;
import com.baby.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @PostMapping(value = "/audit")
    public Object audit(Borrow borrow){
        Map<String,Object> result = new HashMap<>();
        Bid bid=new Bid();
        try{
            if(borrow.getBorrowState()==31){// 放款审核拒绝

                bid.setBorrowId(borrow.getId());
                List<Bid> bidList=bidService.getByBorrowId(bid);//根据borrowId查询bid表信息

                int borrowModify=borrowService.modifyBorrow(borrow);//修改borrow状态

                for (Bid bid1:bidList){
                    //退款
                    UserWallet userWallet=userService.selectBabyUserwallet(bid1.getBidUserId());//用户钱包
                    userWallet.setAvailableAmount(XianXiHouBeng.jia(bid1.getBidAmount(),userWallet.getAvailableAmount()));
                    userWallet.setFreezeAmount(XianXiHouBeng.jian(bid1.getBidAmount(),userWallet.getFreezeAmount()));
                    int money=userService.updateBabyUserwallt(userWallet);

                    //添加账户流水
                    AccountFlow accountFlow=new AccountFlow();
                    accountFlow.setAccountId((bid1.getBidUserId()));
                    accountFlow.setAmount(bid1.getBidAmount());
                    accountFlow.setFlowType(50);
                    accountFlow.setAvailableAmount(userWallet.getAvailableAmount());
                    accountFlow.setFreezeAmount(userWallet.getFreezeAmount());
                    accountFlow.setRemark("退款【"+borrow.getTitle()+"】, 成功，退款金额："+XianXiHouBeng.chu(bid1.getBidAmount(),100)+"元");
                    accountFlow.setCreateTime(new Date());
                    int num=accountFlowService.insterRepaymentFlow(accountFlow);
                }
                result.put("code", 200);
            }else{// 放款审核通过

            }
        }catch (Exception e){
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
