package com.baby.service.bid;

import com.baby.dao.bid.BidMapper;
import com.baby.pojo.Bid;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bidService")
public class BidServiceImpl implements BidService {
    @Resource
    private BidMapper bidMapper;
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
}
