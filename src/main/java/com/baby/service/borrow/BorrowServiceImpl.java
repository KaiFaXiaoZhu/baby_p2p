package com.baby.service.borrow;


import com.baby.common.StringUtil;
import com.baby.dao.borrow.BorrowMapper;
import com.baby.pojo.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("borrowService")
public class BorrowServiceImpl implements BorrowService{

    @Resource
    private BorrowMapper borrowMapper;


    //查询标的基本信息
    @Override
    public List<Borrow> getBorrowList(Borrow borrow) {
        List<Borrow> borrowList=borrowMapper.getBorrowList(borrow);
        return borrowList;
    }
}
