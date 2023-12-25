package com.java.test;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.bean.Student;

public class ListTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-collection.xml");
		Student student = context.getBean("student", Student.class);

		System.out.println(Arrays.toString(student.getArray()));
		System.out.println(student.getSet());
		System.out.println(student.getList());
		System.out.println(student.getMap());
	}
}
