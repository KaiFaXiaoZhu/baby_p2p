package com.baby.controller.loan;

import com.baby.pojo.Borrow;
import com.baby.service.accountFlow.AccountFlowService;
import com.baby.service.borrow.BorrowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;


/**
 * 满标审核
 */
@Controller
@RequestMapping(value = "/finance/loan")
public class LoanController {


    @Resource
    private BorrowService borrowService;
    @Resource
    private AccountFlowService accountFlowService;

    @PostMapping(value = "/audit")
    public Object audit(Borrow borrow){
        if(borrow.getBorrowState()==31){// '放款审核拒绝',

        }else{// '放款审核通过',

        }
        return null;
    }

}
