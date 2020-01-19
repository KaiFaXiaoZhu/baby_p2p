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

    /**
     * 显示我的还款列表
     * @param borrowUserId
     * @return
     */
    @Override
    public List<Repayment> getRepaymentList(String borrowUserId) {
        return repaymentMapper.getRepaymentList(borrowUserId);
    }

    /**
     * 根据borrowId查询还款信息
     * @param borrowId
     * @return
     */
    @Override
    public List<Repayment> getByBorrowId(String borrowId) {
        return repaymentMapper.getByBorrowId(borrowId);
    }


    /**
     * 在我的还款里点击立即还款按钮
     * @param id
     * @param userId
     * @return
     */
    @Override
    public boolean updateRepayment(String id, String userId) {
        if (userMapper.getavailableAmount(id,userId)>0){
            if(repaymentMapper.updateRepayment(id,userId)>0){
                return true;
            }
        }
            return false;
    }

    /**
     * 执行还款操作，更新还款表内数据
     * @param id
     * @return
     */
    @Override
    public boolean updateRepayment(String id) {
        return false;
    }

}
