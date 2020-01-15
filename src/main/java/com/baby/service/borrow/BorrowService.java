package com.baby.service.borrow;

import com.baby.common.Page;
import com.baby.pojo.Borrow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BorrowService {

    public List<Borrow> getBorrowList(Map<String,Object> map);

    public Integer getCountBorrow();
}
