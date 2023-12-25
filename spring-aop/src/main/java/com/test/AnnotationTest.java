package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.BankService;

public class AnnotationTest {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-annotation.xml");
		BankService bankService = context.getBean(BankService.class);

		bankService.get(1);


	}
}
