package com.java.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//Controller注解,表示当前类用于处理请求
//对应xml文件spring-mvc3和spring-mvc4
@Controller
@RequestMapping("/cart")
public class CartController {
	
	//使用RequestMapping注解指定该方法处理的url
	@RequestMapping("/all")
	public void selectAll() {
		System.out.println("查询购物车所有商品信息");
	}
	
	//方法返回值为Stirng,默认是指定跳转的url,默认采用请求转发
	@RequestMapping("/update")
	public String update() {
		System.out.println("请求转发");
		return "/index.html";
	}
	
	//方法返回值为Stirng,在返回字符串前加redirect:表示重定向
	@RequestMapping("/update2")
	public String update2() {
		System.out.println("重定向");
		return "redirect:/index.html";
	}
	
	//如果方法有返回值,默认返回给浏览器的都是url
	@RequestMapping("/add")
	public ModelAndView add() {
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("/user.jsp");
		modelAndView.addObject("name","zhangsan");
		return modelAndView;
		
	}
	
	

}
