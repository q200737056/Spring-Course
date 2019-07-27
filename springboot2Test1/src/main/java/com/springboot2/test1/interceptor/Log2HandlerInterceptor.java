package com.springboot2.test1.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
/**
 * 拦截器
 * @author 课间指针
 *
 */
public class Log2HandlerInterceptor implements HandlerInterceptor {
	/**
	 * Controller方法前 调用
	 * 当之中一个拦截器的preHandle返回false时，整个请求就会结束
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Log2HandlerInterceptor——preHandle方法调用");
		return true;
	}
	/**
	 * Controller方法后 调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Log2HandlerInterceptor——postHandle方法调用");
	}
	/**
	 * 在视图渲染完成后回调,主要用来资源清理工作
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Log2HandlerInterceptor——afterCompletion方法调用");
	}
}
