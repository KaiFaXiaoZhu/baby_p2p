package com.baby.controller.loan;

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
                int borrowModify=borrowService.modifyBorrow(borrow);

                //退款
                bidController.TuiK(bidList,borrow);

                result.put("code", 200);
            }else{// 放款审核通过

            }
        }catch (Exception e){
            result.put("msg", e.getMessage());
        }
        return result;
    }

}
