package com.springboot2.test5;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 课间指针   SpingBoot教程五
 * @SpringBootApplication注解 包含了以下3个注解
 * @ComponentScan ：自动扫描注册
 * @SpringBootConfiguration：与@Configuration作用相同，都是用来声明当前类是一个配置类
 * @EnableAutoConfiguration：实现自动化配置的核心注解
 * @MapperScan: 扫描mapper接口
 * 
 */
@SpringBootApplication
@MapperScan("com.springboot2.test5.dao")
public class App {
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
        System.out.println("启动成功。。。。。。。。。。。");
    }
}
