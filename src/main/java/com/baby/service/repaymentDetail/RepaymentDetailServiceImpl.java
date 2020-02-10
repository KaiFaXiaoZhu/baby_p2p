package com.baby.service.repaymentDetail;

import com.baby.dao.repaymentDetail.RepaymentDetailMapper;
import com.baby.pojo.RepaymentDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RepaymentDetailServiceImpl implements RepaymentDetailService {

    @Resource
    private RepaymentDetailMapper repaymentDetailMapper;
    @Override
    public Integer addRepaymentDetail(RepaymentDetail repaymentDetail) {
        return repaymentDetailMapper.insertRepaymentDetail(repaymentDetail);
    }
}
