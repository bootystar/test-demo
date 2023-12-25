package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.BankService;

public class SchemaTest {

public static void main(String[] args) {
	ApplicationContext context=new ClassPathXmlApplicationContext("spring-aop.xml");
	BankService service = context.getBean("bankService",BankService.class);
	//若指定类实现了接口,会调用jdk自带动态代理(接口实现)生成对象
	//若指定类没有实现接口,会调用cglib动态代理(子类继承)生成指定对象
	System.out.println(service.getClass());
	Integer transfer = service.transfer();
	service.save();
	service.get(100);
	
}
}
