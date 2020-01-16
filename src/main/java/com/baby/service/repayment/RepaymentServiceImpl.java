package com.baby.service.repayment;

import com.baby.dao.repayment.RepaymentMapper;
import com.baby.pojo.Repayment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service("RepaymentService")
public class RepaymentServiceImpl implements RepaymentService {

    @Resource
    private RepaymentMapper repaymentMapper;

    @Override
    public List<Repayment> getRepaymentList(String borrowUserId) {
        return repaymentMapper.getRepaymentList(borrowUserId);
    }

    //根据borrowId查询还款信息
    @Override
    public List<Repayment> getByBorrowId(String borrowId) {
        return repaymentMapper.getByBorrowId(borrowId);
    }
}
