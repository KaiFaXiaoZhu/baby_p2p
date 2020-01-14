package com.baby;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.baby.dao")
public class BabyP2pApplication {

    public static void main(String[] args) {
        SpringApplication.run(BabyP2pApplication.class, args);
    }

}
