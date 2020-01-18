package com.baby.service.repayment;

import com.baby.dao.repayment.RepaymentMapper;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.Repayment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Service("RepaymentService")
public class RepaymentServiceImpl implements RepaymentService {

    @Resource
    private RepaymentMapper repaymentMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<Repayment> getRepaymentList(String borrowUserId) {
        return repaymentMapper.getRepaymentList(borrowUserId);
    }

    //根据borrowId查询还款信息
    @Override
    public List<Repayment> getByBorrowId(String borrowId) {
        return repaymentMapper.getByBorrowId(borrowId);
    }

    @Override
    public boolean updateRepayment(String id, String userId) {
        if (userMapper.getavailableAmount(id,userId)>0){
            if(repaymentMapper.updateRepayment(id,userId)>0){
                return true;
            }
        }
            return false;
    }

    @Override
    public boolean updateRepayment(String id) {
        return false;
    }

}
