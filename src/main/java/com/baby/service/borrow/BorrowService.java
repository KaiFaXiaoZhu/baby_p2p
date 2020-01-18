package com.baby.service.borrow;

import com.baby.common.Page;
import com.baby.pojo.Borrow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BorrowService {

    //查询借款信息
    public List<Borrow> getBorrowList(Map<String,Object> map);
    //查询信息数量
    public Integer getCountBorrow();
    //根据Id查询借款信息
    public Borrow getBorrowId(String id);
}
