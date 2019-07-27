package com.springboot2.test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication注解 包含了以下3个注解
 * @ComponentScan ：自动扫描注册
 * @SpringBootConfiguration：与@Configuration作用相同，都是用来声明当前类是一个配置类
 * @EnableAutoConfiguration：实现自动化配置的核心注解
 * 
 */
@SpringBootApplication
public class App {
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
        System.out.println("启动成功。。。。。。。。。。。");
    }
}
