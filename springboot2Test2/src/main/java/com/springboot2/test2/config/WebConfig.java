package com.springboot2.test2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
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
	 * @ConfigurationProperties：可以把指定的配置信息下的k-v自动封装成实体类(k就是类属性)，可以直接使用在@bean注解上
	 * @return
	 */
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource druidDataSource(){
	      return new DruidDataSource();
	}
	/**
     * 配置监控服务器
     * @return 返回监控注册的servlet对象
     * 
     */
    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = 
        		new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 添加IP白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 添加IP黑名单，当白名单和黑名单重复时，黑名单优先级更高
        servletRegistrationBean.addInitParameter("deny", "");
        // 添加控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }
    /**
     * 配置服务过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> statFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = 
        		new FilterRegistrationBean<>(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }
}
