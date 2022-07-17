package com.example.zrydemo.controller;

import net.sf.jsqlparser.statement.create.table.Index;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 没有权限 异常
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String processUnauthenticatedException(HttpServletRequest request, UnauthorizedException e) {
        System.out.println("没有权限异常111");
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("403");
        return "403";
    }

    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String processAuthorizationExceptionException(HttpServletRequest request, ArrayIndexOutOfBoundsException e) {
        System.out.println("数组越界异常："+e);
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("403");
        return "404";
    }

    @ExceptionHandler({ArithmeticException.class})
    @ResponseStatus(HttpStatus.PAYMENT_REQUIRED)
    @ResponseBody
    public String dd(HttpServletRequest request,ArithmeticException e){
        System.out.println("除数0异常====="+e);
        return "900";
    }
}
