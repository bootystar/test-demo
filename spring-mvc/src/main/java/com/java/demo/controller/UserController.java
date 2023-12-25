package com.java.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//对应xml文件为spring-mvc,处理请求的service类
public class UserController implements Controller{

	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		
		System.out.println("正在处理请求");
		//ModelAndView 用来返回数据的类
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/user.jsp");
		modelAndView.addObject("name","zhangsan");
		return modelAndView;
	}

}
