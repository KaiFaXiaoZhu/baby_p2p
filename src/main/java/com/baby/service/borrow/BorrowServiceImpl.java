package com.baby.service.borrow;


import com.baby.common.Page;
import com.baby.common.StringUtil;
import com.baby.dao.borrow.BorrowMapper;
import com.baby.pojo.Borrow;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("borrowService")
public class BorrowServiceImpl implements BorrowService{

    @Resource
    private BorrowMapper borrowMapper;


    //查询借款信息
    @Override
    public List<Borrow> getBorrowList(Map<String,Object> map) {
        List<Borrow> borrowList=borrowMapper.getBorrowList(map);
        return borrowList;
    }

    //查询信息数量
    @Override
    public Integer getCountBorrow() {
        return borrowMapper.getCountBorrow();
    }

    @Override
    public Borrow getBorrowId(String id) {
        return borrowMapper.getBorrowId(id);
    }

}
