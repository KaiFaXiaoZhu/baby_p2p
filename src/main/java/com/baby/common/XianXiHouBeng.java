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
                .divide(new BigDecimal(totalmonth),2,BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal(totalmonth));
        return monthIncome.doubleValue();
    }

    public static Integer jia(double money,double moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).add(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer jian(double money,double moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).subtract(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer cheng(double money,double moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).multiply(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }
    public static Integer chu(double money,double moneyNum) {
        BigDecimal monthIncome = new BigDecimal(money).divide(new BigDecimal(moneyNum));
        return monthIncome.intValue();
    }

}
