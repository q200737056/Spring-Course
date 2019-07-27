package com.springboot2.test1.config;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.springboot2.test1.filter.CustomFilter;
import com.springboot2.test1.interceptor.Log2HandlerInterceptor;
import com.springboot2.test1.interceptor.LogHandlerInterceptor;
/**
 *  @Configuration注解: 配置文件
 * @author 课间指针
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	/**
	 * addResourceHandlers方法处理静态资源  如图片，js，css,页面等
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		//当你请求http://127.0.0.1:8080/page/index.html时，会把resources/static/page/index.html返回。
		//这里的静态资源是放置在WEB-INF目录下的
		//一般情况下，WEB-INF目录下的资源是禁止直接访问的
		//其实springboot默认 已经设置了 处理静态资源路径
		// 默认 /**  配置classpath:/static,classpath:/public,classpath:/resources,
		//classpath:/META-INF/resources,servlet context:/
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	/**
	 * addViewControllers方法 可以很方便的实现一个请求到视图的映射
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//访问http://127.0.0.1:8080/时，会直接返回index.html页面
		registry.addViewController("/").setViewName("forward:"+"/page/index.html");
		
	}
	/**
	 * addInterceptors方法 设置拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 拦截 http://127.0.0.1:8080/index/ 的所有请求
		//拦截器 按添加的顺序从前向后 依次执行preHandle，而postHandle，afterCompletion相反，从后向前
		//addPathPatterns设置拦截路径  excludePathPatterns设置不拦截路径
		registry.addInterceptor(new LogHandlerInterceptor()).addPathPatterns("/index/**")
			.excludePathPatterns("/index/system/**");
		registry.addInterceptor(new Log2HandlerInterceptor()).addPathPatterns("/index/**");
	}
	/**
	 * 配置过滤器 Filter
	 * @Bean注解相当于 xml中的<bean>,方法名customFilter 即bean的id 
	 * 方法的参数名  即根据type依赖注入。
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean customFilter() {
		 FilterRegistrationBean registration = new FilterRegistrationBean();
		 //拦截的请求类型
		 registration.setDispatcherTypes(DispatcherType.REQUEST);
		 //加入过滤器
		 registration.setFilter(new CustomFilter());
		 //拦截路径
		 registration.addUrlPatterns("/*");
		 //设置过滤器名称
		 registration.setName("customFilter");
		 //设置过滤器执行顺序  值越大，执行顺序越靠后
		 registration.setOrder(Integer.MAX_VALUE);
		 return registration;
	}
	
}
