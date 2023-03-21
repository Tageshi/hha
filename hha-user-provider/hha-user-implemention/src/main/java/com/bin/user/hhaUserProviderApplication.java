package com.bin.user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tageshi
 * @date 2023/3/15 15:14
 */
@EnableDubbo
@SpringBootApplication
@MapperScan("com.bin.user.dao")
public class hhaUserProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(hhaUserProviderApplication.class, args);
    }
}