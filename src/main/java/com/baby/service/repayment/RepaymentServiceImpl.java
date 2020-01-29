package com.baby.service.repayment;

import com.baby.dao.accountFlow.AccountFlowMapper;
import com.baby.dao.repayment.RepaymentMapper;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.AccountFlow;
import com.baby.pojo.Repayment;
import com.baby.pojo.RepaymentDetail;
import com.baby.pojo.UserWallet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("RepaymentService")
public class RepaymentServiceImpl implements RepaymentService {

    @Resource
    private RepaymentMapper repaymentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private AccountFlowMapper accountFlowMapper;

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
        List<RepaymentDetail> list = new ArrayList<RepaymentDetail>();
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        AccountFlow accountFlow = new AccountFlow();
        UserWallet userWallet = new UserWallet();
        try {
            if (userMapper.getavailableAmount(id,userId)>0) { //判断账户是否有足够的金额还款
                if (userMapper.updateRepayment(id, userId) > 0) { //扣除还款人钱包的钱
                    userWallet = userMapper.selectBabyUserwallet(userId);
                    Repayment repayment = repaymentMapper.RunningWaterTitle(id);
                    accountFlow.setAccountId(userId);
                    accountFlow.setRemark("还款"+repayment.getBorrowTitle()+"成功，还款金额："+df.format((float)repayment.getTotalAmount()/100)+"元");
                    accountFlow.setAmount(repayment.getTotalAmount());
                    accountFlow.setFlowType(30);
                    accountFlow.setAvailableAmount(userWallet.getAvailableAmount());
                    accountFlow.setCreateTime(new Date());
                    accountFlow.setFreezeAmount(userWallet.getFreezeAmount());
                    accountFlowMapper.insterRepaymentFlow(accountFlow); //创建还款款流水账单
                    list = repaymentMapper.BorrowerInformation(id); //获取投标人信息
                    if(repayment.getState()==1){    //判断是否逾期过，逾期过改为逾期已还
                        repaymentMapper.RepaymentStatus(4,id);
                    }else{
                        repaymentMapper.RepaymentStatus(3,id);
                    }
                    for (int i = 0; i < list.size(); i++) {          //循环增加钱
                        if (accountFlow != null || userWallet != null) {
                            accountFlow = new AccountFlow();
                            userWallet = new UserWallet();
                        }
                        userMapper.updateRepaymentAdd(list.get(0)); //投标人钱包用户金额的增加
                        userWallet = userMapper.selectBabyUserwallet(list.get(i).getBidUserId());
                        accountFlow.setAccountId(list.get(i).getBidUserId());
                        accountFlow.setRemark("回款"+list.get(i).getBorrowTitle()+"成功，回款金额："+df.format((float)list.get(i).getTotalAmount()/100)+"元");
                        accountFlow.setAmount(list.get(i).getTotalAmount());
                        accountFlow.setFlowType(41);
                        accountFlow.setAvailableAmount(userWallet.getAvailableAmount());
                        accountFlow.setCreateTime(new Date());
                        accountFlow.setFreezeAmount(userWallet.getFreezeAmount());
                        accountFlowMapper.insterRepaymentFlow(accountFlow); //创建回款流水账单
                    }
                    return true;
                }
            }
             }catch (Exception e) {
            e.printStackTrace();
        }
            return false;
    }

    @Override
    public List<Repayment> AllRepayments() {
        return repaymentMapper.AllRepayments();
    }

    @Override
    public int RepaymentStatus(int state,String id) {
        return repaymentMapper.RepaymentStatus(state,id);
    }

}
