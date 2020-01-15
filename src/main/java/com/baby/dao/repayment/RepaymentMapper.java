package com.baby.dao.repayment;

import com.baby.pojo.Repayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 还款的Mapper层
 */
public interface RepaymentMapper {

    public List<Repayment> getRepaymentList(@Param("borrowUserId") String borrowUserId);

}
