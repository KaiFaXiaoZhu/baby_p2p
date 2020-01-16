package com.baby.service.repayment;

import com.baby.pojo.Repayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepaymentService {

    public List<Repayment> getRepaymentList(String borrowUserId);

    //根据borrowId查询还款信息
    public List<Repayment> getByBorrowId(@Param("borrowId") String borrowId);
}
