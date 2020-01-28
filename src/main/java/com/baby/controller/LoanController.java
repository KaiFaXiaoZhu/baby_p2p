package com.baby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 满标审核
 */
@Controller
@RequestMapping(value = "/finance/loan")
public class LoanController {

    @PostMapping(value = "/audit")
    public Object audit(){
        return null;
    }

}
