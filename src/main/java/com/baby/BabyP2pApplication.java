package com.baby;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.baby.dao")
@EnableScheduling
public class BabyP2pApplication {
    public static void main(String[] args) {
        SpringApplication.run(BabyP2pApplication.class, args);
    }

}
