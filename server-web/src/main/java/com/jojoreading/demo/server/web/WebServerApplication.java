package com.jojoreading.demo.server.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.jojoreading.demo.server"})
@MapperScan(basePackages = {"com.jojoreading.demo.server.core.dao"})
public class WebServerApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .sources(WebServerApplication.class)
                .build();
		springApplication.run(args);
    }

}
