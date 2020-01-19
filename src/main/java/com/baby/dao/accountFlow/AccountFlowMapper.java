package com.baby.dao.accountFlow;

import com.baby.pojo.AccountFlow;

public interface AccountFlowMapper {
    /**
     * 生成还款流水账单
     */
    public int insterRepaymentFlow(AccountFlow accountFlow);
}
