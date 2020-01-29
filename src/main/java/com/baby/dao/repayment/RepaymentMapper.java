package com.baby.dao.repayment;

import com.baby.pojo.Repayment;
import com.baby.pojo.RepaymentDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 还款的Mapper层
 */
public interface RepaymentMapper {
    //显示我的还款列表
    public List<Repayment> getRepaymentList(@Param("borrowUserId") String borrowUserId);

    //根据borrowId查询还款信息
    public List<Repayment> getByBorrowId(@Param("borrowId") String borrowId);

    //获取流水信息
    public Repayment RunningWaterTitle(@Param("id")String id);

    //查询所有投标人信息
    public List<RepaymentDetail> BorrowerInformation(@Param("id")String id);

    //查询所有还款信息
    public List<Repayment> AllRepayments();

    //更新还款状态
    public int RepaymentStatus(@Param("state")int status,@Param("id")String id);
}
