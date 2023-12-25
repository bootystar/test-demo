package com.advice;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class BankServiceBeforeAdvice implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		
		System.out.println(arg3.getClass());
		System.out.println("方法执行之后执行(后置通知)");
		
	}
	
}
