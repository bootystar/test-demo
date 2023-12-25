package com.java.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.servlet.UserServlet;

public class DaoTest {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-context-DAO.xml");
		System.out.println(context);
		UserServlet servlet = context.getBean("userServlet",UserServlet.class);
		
	}
}
