package com.baby.dao.bid;

import com.baby.pojo.Bid;

import java.util.List;

public interface BidMapper {

    //根据borrowId查询借款信息
    public List<Bid> getByBorrowId(Bid bid);

}
