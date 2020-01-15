package com.baby.service.repayment;

import com.baby.pojo.Repayment;

import java.util.List;

public interface RepaymentService {

    public List<Repayment> getRepaymentList(String borrowUserId);

}
