package com.goj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ReggieApplication {

    public static void main(String[] args) {
        String name="reggie";
        log.info("{}项目启动中.....",name);
        SpringApplication.run(ReggieApplication.class, args);
    }
}
