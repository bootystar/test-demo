package com.java.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.bean.User;

public class Base {
	public static void main(String[] args) throws Exception {
//		创建对象的一般方式
		User user=new User();
	
//		反射创建对象
		Class<?> clazz =Class.forName("com.java.bean.User");
		user=(User)clazz.newInstance();
		System.out.println(user);
		System.out.println("----------------------------");
//		利用spring创建对象
//		加载spring配置文件,获取spring的上下文对象conetext
		ApplicationContext context=new ClassPathXmlApplicationContext("spring-context.xml");
//		获取use对象
		user=(User)context.getBean("user");
		System.out.println(user);
		user=(User)context.getBean("user2");
		System.out.println(user);
		user=(User)context.getBean("user3");
		System.out.println(user);
//		user=(User)context.getBean("user4");
//		工厂方法创建对象,需要先行在类中指定工厂创建构造器:
//		public static User getInstance(int uid,String uname) {
//			System.out.println("调用工厂方法");
//			return new User(uid, uname);
//		}
		
		
		
	}
	
}
