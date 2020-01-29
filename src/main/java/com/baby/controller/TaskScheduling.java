package com.baby.controller;

import com.baby.pojo.Repayment;
import com.baby.service.repayment.RepaymentService;
import com.baby.service.user.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class TaskScheduling {

    private static final int creditScore = 1;    //扣除的信用得分

    private static final Integer creditLine = 10000;    //扣除的授信额度(单位：分)

    private static final Integer residualCreditLine = 10000;    //扣除剩余授信额度(单位：分)

    @Resource
    private RepaymentService repaymentService;

    @Resource
    private UserService userService;

    @Scheduled(initialDelay = 2000,fixedDelay = 30000)// 表示首次任务启动的延迟时间。
    public void initialDelay(){
        List<Repayment> list = repaymentService.AllRepayments();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTwo = new Date();
        for (int i=0;i<list.size();i++){
            if(dateTwo.getTime()>=list.get(i).getDeadline().getTime()){//比较时间是否逾期
                if(list.get(i).getState()==2){ //状态为待还才进入
                    repaymentService.RepaymentStatus(1,list.get(i).getId());//更新状态为逾期
                    userService.DeductCreditScore(creditScore,creditLine,residualCreditLine,list.get(i).getBorrowUserId());//更新用户信息
                }
            }
        }
    }
}
