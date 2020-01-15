package com.baby.dao.borrow;


import com.baby.common.Page;
import com.baby.pojo.Borrow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 标的信息mapper层
 */
public interface BorrowMapper {

    //查询标的基本信息
    public List<Borrow> getBorrowList(Map<String,Object> map);
    //查询标的数量
    public Integer getCountBorrow();
}
