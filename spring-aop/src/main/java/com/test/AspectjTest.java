package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.BankService;

public class AspectjTest {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-aop2.xml");
		BankService bankService = context.getBean("bankService",BankService.class);
		bankService.transfer();
		bankService.get(0);
		
	}
}
