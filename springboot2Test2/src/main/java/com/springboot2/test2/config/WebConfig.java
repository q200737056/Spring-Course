package com.springboot2.test2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
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
		//访问http://127.0.0.1:8080/index/
		registry.addViewController("/").setViewName("forward:"+"/index");
		
	}
	
}
