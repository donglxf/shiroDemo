package com.example.zrydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.zrydemo.mapper")
public class ZryDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZryDemoApplication.class, args);
    }

}
