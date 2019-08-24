package com.springboot2.test5.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.druid.pool.DruidDataSource;


/**
 *  @Configuration注解: 配置文件
 * @author 课间指针
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * 配置启动页
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/").setViewName("forward:"+"/index");
		
	}
	/**
	 * 手动配置数据源
	 * @return
	 */
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource druidDataSource(){
	      return new DruidDataSource();
	}
	
	
	/**
	 * 默认已经配置了H2登陆界面的servlet，拦截路径/h2-console/*
	 * 当然也可以自定义重新配置
	 * 
	 * @return
	 */
	/*@Bean
    public ServletRegistrationBean<WebServlet> h2ServletRegistrationBean(){
        ServletRegistrationBean<WebServlet> registrationBean = 
        		new ServletRegistrationBean<>(new WebServlet(),"/h2/*");
        return registrationBean;
    }*/
	
}
