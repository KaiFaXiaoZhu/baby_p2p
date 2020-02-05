package com.baby.dao.borrow;


import com.baby.common.Page;
import com.baby.pojo.Bid;
import com.baby.pojo.Borrow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 标的信息mapper层
 */
public interface BorrowMapper {

    //查询借款信息分页
    public List<Borrow> getBorrowListLimit(Map<String, Object> map);
    //查询标的数量
    public Integer getCountBorrow();
    //根据Id查询借款信息
    public Borrow getBorrowId(@Param("id") String id);
    //新增Borrow
    public Integer insertBorrow(Borrow borrow);
    //修改Borrow
    public Integer updateBorrow(Borrow borrow);
    //申请借款
    public Integer AddLoan(Borrow borrow);
    //更新审核借款状态
    public Integer updateLoan(Bid bid);
}
