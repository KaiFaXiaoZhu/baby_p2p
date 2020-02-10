package com.baby.service.repayment;

import com.baby.pojo.Repayment;
import com.baby.pojo.RepaymentDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RepaymentService {
    //显示我的还款列表
    public List<Repayment> getRepaymentList(String borrowUserId,Integer current,Integer size);

    //根据borrowId查询还款信息
    public List<Repayment> getByBorrowId(@Param("borrowId") String borrowId);

    //在我的还款里点击立即还款按钮
    public boolean updateRepayment(String id,String userId);

    //计算数据的总数量
    public Integer repaymentCount();

    //计算还款详细表数据数量
    public Integer countDetails();

    //查询所有还款信息
    public List<Repayment> AllRepayments();

    //更新还款状态
    public int RepaymentStatus(int status,String id);

    //添加还款信息
    public Integer addRepayment(Repayment repayment);

    //借款人id查询还款明细
    public List<RepaymentDetail> collectionDetails(String bidUserId, Integer current, Integer size);

}
