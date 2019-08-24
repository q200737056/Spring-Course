package com.springboot2.test1.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * @Aspect：切面
 * @author Administrator
 *
 */
@Aspect
@Component
@Order(2)
public class TimeAspect {
	/**
	 * 切入点
	 * execution：用于匹配子表达式
	 *   *：匹配所有字符
		 ..：一般用于匹配多个包，多个参数
		 +：表示类及其子类
	 * within：用于匹配连接点所在的Java类或者包。within(com.springboot2.test1.controller)
	 * @annotation ：匹配连接点被它参数指定的Annotation注解的方法。
	 * @annotation(com.springboot2.test1.CustAnnotation)
	 */
	@Pointcut("execution(public * com.springboot2.test1.controller..*.*(..))")
    public void info() {
    }
	/**
	          使用@Before在切入点开始处切入内容
		使用@After在切入点结尾处切入内容
		使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
		使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
		使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
		 
	 */
	@Before("info()")
	public void before(){
		System.out.println("AOP开始："+System.currentTimeMillis());
	}
	@After("info()")
    public void after() {
        System.out.println("AOP结束："+System.currentTimeMillis());
      
    }
}
