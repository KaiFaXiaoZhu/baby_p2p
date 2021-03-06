package com.baby.controller.repayment;

import com.baby.common.BorrowPage;
import com.baby.pojo.Borrow;
import com.baby.pojo.Repayment;
import com.baby.pojo.RepaymentDetail;
import com.baby.pojo.UserAccount;
import com.baby.service.borrow.BorrowService;
import com.baby.service.repayment.RepaymentService;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Resource
    private BorrowService borrowService;

    //查询我的还款
    @RequestMapping(value = "query")
    @ResponseBody
    public Object getListRepayment(HttpSession session, @RequestParam(required = false) Integer currentPage) {
        Map<String, Object> result = new HashMap<>();
        String borrowUserId = "";
        BorrowPage<Repayment> borrowPage = new BorrowPage<Repayment>();
        int num = repaymentService.repaymentCount();//信息数量
        try {
            if (session.getAttribute("user") != null) {
                UserAccount userAccount = (UserAccount) session.getAttribute("user");
                borrowUserId = userAccount.getId();
            }
            borrowPage.setPageSize(5);
            borrowPage.setCurrentPage(currentPage == null ? 1 : currentPage);
            //计算当前页
            currentPage = (borrowPage.getCurrentPage() - 1) * borrowPage.getPageSize();
            List<Repayment> listData = repaymentService.getRepaymentList(borrowUserId, currentPage, borrowPage.getPageSize());
            borrowPage.setTotalPage((num + borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data", borrowPage);
            result.put("code", "200");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("msg", e.getMessage());
        }
        return result;
    }

    //根据borrowId查询还款信息
    @PostMapping(value = "/getByBorrowId")
    @ResponseBody
    public Object findByBorrowId(String borrowId) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Repayment> repayments = repaymentService.getByBorrowId(borrowId);
            result.put("data", repayments);
            result.put("code", 200);
        } catch (Exception e) {
            result.put("msg", e.getMessage());
        }
        return result;
    }

    /**
     * 点击立即还款
     */
    @RequestMapping(value = "/repay")
    @ResponseBody
    public Object Repayment(@RequestParam(value = "id") String id, @RequestParam(value = "userId") String userId) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (repaymentService.updateRepayment(id, userId)) {
                result.put("code", 200);
            } else {
                result.put("msg", "账户余额不足");
            }
        } catch (Exception e) {
            result.put("msg", e.getMessage());
        }
        return result;
    }

    /**
     * 加载收款明细
     */
    @PostMapping(value = "/detail/query")
    @ResponseBody
    public Object collectionDetails(HttpSession session, @RequestParam(required = false) Integer currentPage) {
        Map<String, Object> result = new HashMap<>();
        String bidUserId = "";
        BorrowPage<RepaymentDetail> borrowPage = new BorrowPage<RepaymentDetail>();
        int num = repaymentService.countDetails();//信息数量
        try {
            if (session.getAttribute("user") != null) {
                UserAccount userAccount = (UserAccount) session.getAttribute("user");
                bidUserId = userAccount.getId();
            }
            borrowPage.setPageSize(5);
            borrowPage.setCurrentPage(currentPage == null ? 1 : currentPage);
            //计算当前页
            currentPage = (borrowPage.getCurrentPage() - 1) * borrowPage.getPageSize();
            List<RepaymentDetail> listData = repaymentService.collectionDetails(bidUserId, currentPage, borrowPage.getPageSize());
            borrowPage.setTotalPage((num + borrowPage.getPageSize() - 1) / borrowPage.getPageSize());
            borrowPage.setListData(listData);
            result.put("data", borrowPage);
            result.put("code", "200");
        } catch (Exception e) {
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
