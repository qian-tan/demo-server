package com.jojoreading.demo.server.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.jojoreading.demo.server"})
@MapperScan(basePackages = {"com.jojoreading.demo.server.core.dao"})
public class ServerBaseApplication {

}
