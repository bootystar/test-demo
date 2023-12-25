package com.java.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.java.service.UserService;

import lombok.Data;

/*
 * 使用Component注解开发 :
 *  在注解内可以使用value指定id(别名)
 *  不指定时默认以当前类名首字母小写作为id
 *  
 *使用注解后需要在spring的配置文件中开启注解扫描
 *
 *在对应的属性上面使用autowired注解让其自动关联属性对应的对象
 */
@Data
@Controller
public class UserServlet {
	@Autowired
	private UserService userService;

	public void doGet() {
		userService.add(null);
	}


	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
