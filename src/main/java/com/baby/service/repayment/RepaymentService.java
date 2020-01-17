package com.baby.service.repayment;

import com.baby.pojo.Repayment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepaymentService {
    //显示我的还款列表
    public List<Repayment> getRepaymentList(String borrowUserId);

    //根据borrowId查询还款信息
    public List<Repayment> getByBorrowId(@Param("borrowId") String borrowId);

    //在我的还款里点击立即还款按钮
    public boolean updateRepayment(String id,String userId);

    //执行还款操作，更新还款表内数据
    public boolean updateRepayment(String id);

    //
}
