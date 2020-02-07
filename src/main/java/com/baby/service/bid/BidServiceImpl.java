package com.baby.service.bid;

import com.alibaba.fastjson.JSON;
import com.baby.common.XianXiHouBeng;
import com.baby.dao.accountFlow.AccountFlowMapper;
import com.baby.dao.bid.BidMapper;
import com.baby.dao.user.UserMapper;
import com.baby.pojo.AccountFlow;
import com.baby.pojo.Bid;
import com.baby.pojo.Borrow;
import com.baby.pojo.UserWallet;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("bidService")
public class BidServiceImpl implements BidService {
    @Resource
    private BidMapper bidMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AccountFlowMapper accountFlowMapper;



    @Override
    public List<Bid> getByBorrowId(Bid bid) {
        return bidMapper.getByBorrowId(bid);
    }

    @Override
    public Integer addBid(Bid bid) {
        return bidMapper.insertBid(bid);
    }

    @Override
    public Bid getBidById(String id) {
        return bidMapper.getBidById(id);
    }

    @Override
    public Integer modifyBid(Bid bid) {
        return bidMapper.updateBid(bid);
    }

    @Override
    public Bid getBidByBidUserId(String bidUserId,String borrowId) {
        return bidMapper.getBidByBidUserId(bidUserId,borrowId);
    }

    /**
     * 退款
     * @param bidList
     * @param borrow
     */
    public void TuiK(List<Bid> bidList, Borrow borrow){
        Integer num;
        try{
            for (Bid bid:bidList){
                //修改bid中borrowState
                bid.setBorrowState(borrow.getBorrowState());
                num=bidMapper.updateBid(bid);

                //退款
                UserWallet userWallet=userMapper.selectBabyUserwallet(bid.getBidUserId());//用户钱包
                userWallet.setAvailableAmount(XianXiHouBeng.jia(bid.getBidAmount().toString(),userWallet.getAvailableAmount().toString()));
                userWallet.setFreezeAmount(XianXiHouBeng.jian(userWallet.getFreezeAmount().toString(),bid.getBidAmount().toString()));
                num=userMapper.updateBabyUserwallt(userWallet);

                //添加账户流水
                AccountFlow accountFlow=new AccountFlow(null,bid.getBidUserId(),bid.getBidAmount(),borrow.getBorrowState(),userWallet.getAvailableAmount(),userWallet.getFreezeAmount(),
                        "退款【"+borrow.getTitle()+"】, 成功，解除投标冻结金额："+XianXiHouBeng.chu(bid.getBidAmount().toString(),"100")+"元",new Date());
                num=accountFlowMapper.insterRepaymentFlow(accountFlow);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("TuiK系统异常");
        }
    }
}
