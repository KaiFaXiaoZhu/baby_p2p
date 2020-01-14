package com.baby.dao.borrow;


import com.baby.pojo.Borrow;

import java.util.List;

/**
 * 标的信息mapper层
 */
public interface BorrowMapper {

    //查询标的基本信息
    public List<Borrow> getBorrowList(Borrow borrow);

}
