package com.java.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.bean.User;

@Controller
@RequestMapping("/data")
//用来提交数据的类(mvc测试传参)
//对应spring=mvc3或spring=mvc3
public class DataController {

	// 获取数据
	// 当需要指定参数时(如HttpServletRequest或HttpServletResponse),spring会在运行时传入对应参数
	@RequestMapping("/findById")
	public void findById(HttpServletRequest request) {
		System.out.println("传入参数为HttpServletRequest的方法,自己在方法内使用.getParameter()获取数据");
		System.out.println(request.getParameter("id"));

	}

	// 同理,若需要获取指定数据类型,spring会自动传入相应的数据 原理:spring利用参数的名字作为key
	// 调用HttpServletRequest类的getParameter()方法去获取请求的value
	// 400错误,传递的参数无法转换成指定类型的数据
	@RequestMapping("/findById2")
	public void findById2(int id) {
		System.out.println("传入参数为int的方法");
		System.out.println(id);
	}

	// 多个数据同理
	@RequestMapping("/login")
	public void login(String account, String password) {
		System.out.println("传入多个参数的方法");
		System.out.println(account + "+" + password);
	}

	// spring可以自动将获取的数据封装成对象(指定对象需要由getter和setter)
	@RequestMapping("/regist")
	public void regist(User user) {
		System.out.println("自动封装成对象的方法");
		System.out.println(user);
	}

	// 注解:RequestParam,在传入参数前使用,参数对应的key(前后端名字不一致)
	// 适用场景:前端传入的数据名与后端指定数据名不一致
	// name:前端传入数据名
	// required:是否必须传入,默认false,设置为true时,不传入会报错
	// defaultValue:指定默认值(为传入参数时)
	@RequestMapping("/delete")
	public void delete(@RequestParam(name = "abc", required = true, defaultValue = "1") int id) {
		System.out.println("前端参数与后端参数名不一致的方法(使用注解修改)");
		System.out.println(id);
	}

	
	//返回数据同理,指定参数HttpServletResponse,就能获取
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("返回数据的测试方法");
		String data="{id:1001,name:'lisi'}";
		response.setContentType("application/json;charset:utf-8");
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//注解ResponseBody指定返回json数据
	//等于response.setContentType("application/json;charset:utf-8");
	//需要导入jackson相关依赖包
	@RequestMapping("/test2")
	@ResponseBody
	public User info() {
		System.out.println("返回类型为json数据的方法");
		User user=new User();
		user.setName("zhangsan");
		return user;
		
	}
	
	
	
	
}
