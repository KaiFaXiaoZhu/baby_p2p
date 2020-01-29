package com.baby.service.accountFlow;

import com.baby.dao.accountFlow.AccountFlowMapper;
import com.baby.pojo.AccountFlow;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;

@Service("AccountFlowService")
public class AccountFlowServiceImpl implements AccountFlowService {

    @Resource
    private AccountFlowMapper accountFlowMapper;

    /**
     *生成流水账单
     * @param accountFlow
     * @return
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
