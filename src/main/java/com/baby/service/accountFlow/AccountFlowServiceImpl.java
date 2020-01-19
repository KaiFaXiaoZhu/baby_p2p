package com.baby.service.accountFlow;

import com.baby.dao.accountFlow.AccountFlowMapper;
import com.baby.pojo.AccountFlow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("AccountFlowService")
public class AccountFlowServiceImpl implements AccountFlowService {

    @Resource
    private AccountFlowMapper accountFlowMapper;

    /**
     * 生成还款流水账单
     */
    @Override
    public int insterRepaymentFlow(AccountFlow accountFlow) {
        int insterRepaymentFlow = 0;
        if (accountFlow != null){
            insterRepaymentFlow = accountFlowMapper.insterRepaymentFlow(accountFlow);
        }
        return insterRepaymentFlow;
    }
}
