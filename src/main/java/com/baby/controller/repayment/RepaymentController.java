package com.baby.controller.repayment;

import com.baby.common.BorrowPage;
import com.baby.pojo.Borrow;
import com.baby.pojo.Repayment;
import com.baby.pojo.UserAccount;
import com.baby.service.repayment.RepaymentService;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/finance/repayment")
@Controller
public class RepaymentController {

    @Resource
    private RepaymentService repaymentService;

    //查询我的还款
    @RequestMapping(value = "query")
    @ResponseBody
    public Object getListRepayment(HttpSession session,@RequestParam(required = false) Integer currentPage){
        Map<String,Object> result = new HashMap<>();
        String borrowUserId = "";
        try {
            if(session.getAttribute("user")!=null){
                UserAccount userAccount = (UserAccount)session.getAttribute("user");
                borrowUserId = userAccount.getId();
            }
            if(repaymentService.getRepaymentList(borrowUserId)!=null){
                List<Repayment> listData = repaymentService.getRepaymentList(borrowUserId);
                BorrowPage<Repayment> borrowPage=new BorrowPage<Repayment>();
                borrowPage.setTotalPage(0);
                borrowPage.setCurrentPage(currentPage);
                borrowPage.setPageSize(5);
                borrowPage.setListData(listData);
                result.put("data",borrowPage);
                result.put("code","200");
            }else{
                result.put("code","500");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("msg",e.getMessage());
        }
        return result;
    }

}
