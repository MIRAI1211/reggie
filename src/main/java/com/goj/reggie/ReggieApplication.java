package com.goj.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {

    public static void main(String[] args) {
        String name="reggie";
        log.info("{}项目启动中.....",name);
        SpringApplication.run(ReggieApplication.class, args);
    }
}
