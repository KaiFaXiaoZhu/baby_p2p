package com.baby.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class XianXiHouBeng {

    /**
     *
     *先息后本  总利息
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金和利息,四舍五入，直接截取小数点最后两位
     */
    public static double getXianXiHouBeng(double invest, double yearRate, int totalmonth) {
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(yearRate))
                .divide(new BigDecimal(totalmonth),2,BigDecimal.ROUND_HALF_DOWN)
                .multiply(new BigDecimal(totalmonth));
        return monthIncome.doubleValue();
    }

    /**
     *
     *先息后本  月利息
     *
     * @param invest
     *            总借款额（贷款本金）
     * @param yearRate
     *            年利率
     * @param month
     *            还款总月数
     * @return 每月偿还本金和利息,四舍五入，直接截取小数点最后两位
     */
    public static double getYueLiX(double invest, double yearRate, int totalmonth) {
        BigDecimal monthIncome = new BigDecimal(invest)
                .multiply(new BigDecimal(yearRate))
                .divide(new BigDecimal(totalmonth),2,BigDecimal.ROUND_HALF_DOWN);
        return monthIncome.doubleValue();
    }

    /**
     28 　　　　* <p>Description: 月还款额</p>
     29 　　　　* @param loan 贷款本金
     30 　　　　* @param monthlyInterestRate 月利率
     31 　　　　* @param amount 期数
     32 　　　　* @param curNumber 当前期数
     33 　　　　* @return
     34 　　　　*/
    public static double monthlyRepayment(BigDecimal loan, BigDecimal monthlyInterestRate, int amount, int curNumber){
        double number=loan.multiply(monthlyInterestRate).divide(new BigDecimal(amount),2,BigDecimal.ROUND_HALF_DOWN).doubleValue();
        if(amount==curNumber){
             return loan.add(new BigDecimal(number)).doubleValue();
        }else{
            return number;
        }
    }


    public static Integer jia(String money,String moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).add(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer jian(String money,String moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).subtract(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer cheng(String money,String moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).multiply(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer chu(String money,String moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).divide(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }

}
