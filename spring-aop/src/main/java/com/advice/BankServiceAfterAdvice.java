package com.advice;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class BankServiceAfterAdvice implements MethodBeforeAdvice{

	@Override
	public void before(Method arg0, Object[]arg1, Object arg2) throws Throwable {
		System.out.println("方法之前执行的方法(前置通知)");
		System.out.println(arg2.getClass());
	}

}
