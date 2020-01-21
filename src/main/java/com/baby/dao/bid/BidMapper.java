package com.baby.dao.bid;

import com.baby.pojo.Bid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BidMapper {

    //根据borrowId查询借款信息
    public List<Bid> getByBorrowId(Bid bid);
    //增加bid信息
    public Integer insertBid(Bid bid);
    //根据Id查询
    public Bid getBidById(@Param("id")String id);
    //修改Bid
    public Integer updateBid(Bid bid);
    //查询bid表用户id
    public Bid getBidByBidUserId(@Param("bidUserId") String bidUserId,@Param("borrowId")String borrowId);
}
