package com.baby.service.bid;

import com.baby.pojo.Bid;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BidService {

    //根据borrowId查询借款信息
    public List<Bid> getByBorrowId(Bid bid);
    //增加bid信息
    public Integer addBid(Bid bid);
    //根据Id查询
    public Bid getBidById(String id);
}
