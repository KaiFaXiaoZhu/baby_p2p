package com.baby.service.accountFlow;

import com.baby.pojo.AccountFlow;

public interface AccountFlowService {
    /**
     * 生成流水账单
     */
    public int insterRepaymentFlow(AccountFlow accountFlow);
}
