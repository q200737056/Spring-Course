package com.springboot2.test5.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ControllerAdvice:控制器增强 包含了@Component注解
 * @author Administrator
 * 全局异常处理
 */
@ControllerAdvice
public class DefaultExceptionHandler {
    /**
     * 没有权限 异常
     * @ExceptionHandler:声明一个或多个类型的异常,
     * 当符合条件的Controller抛出这些异常之后将会对这些异常进行捕获
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView handleUnauthenticatedException(UnauthorizedException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("noPerms");
        return mv;
    }
}
