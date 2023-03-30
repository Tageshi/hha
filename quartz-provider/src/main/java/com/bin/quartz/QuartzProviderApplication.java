package com.bin.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author tageshi
 * @date 2023/3/28 20:20
 */
@MapperScan("com.bin.quartz")
@SpringBootApplication
@EnableScheduling
public class QuartzProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzProviderApplication.class,args);
    }
}
