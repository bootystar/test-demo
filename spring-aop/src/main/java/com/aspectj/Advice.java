package com.aspectj;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

//aspectj方案
//在单个类内写出所有需要执行的通知
//每个方法对应一个通知
//注解aspect代表该类是一个切点类,同时需要添加表示注解component,否则该类无法被spring扫描到
@Aspect
@Component
public class Advice {

	//指定切点作用的方法
	@Pointcut("execution(* com.service.*.*(..))")
	public void cp() {}

	// 前置通知
	// 添加切点注解方法名指定切点
	@Before("cp()")
	public void before() {
		System.out.println("前置通知");
	}

	// 后置通知
	@After("cp()")
	public void after() {
		System.out.println("后置通知");

	}

	// 返回后通知
	@AfterReturning("cp()")
	public void afterReturning() {
		System.out.println("返回后通知");

	}

	// 异常通知
	@AfterThrowing("cp()")
	public void except() {
		System.out.println("异常通知");
	}

	// 环绕通知
	@Around("cp()")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// 该方法传入的ProceedingJoinPoint对象是由spring在执行时自动传入的
		// 环绕通知作用原理类似与一个完整的过滤器,需要在方法内指定继续执行才会往下一层方法走
		System.out.println("环绕通知=======在原方法执行之前执行");
		// 使用proceed()继续执行原有方法
		try {
			proceedingJoinPoint.proceed();
		} catch (Throwable e) {
			// 发生异常时可以进行处理,例如记录日志

			// 获取当前执行的方法
			MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

			// 获取发生异常的方法
			Method method = methodSignature.getMethod();

			// 生成日志信息
			String timeInfo = new Date().toString();
			String classInfo = proceedingJoinPoint.getTarget().getClass().toString();
			String methInfo = method.getName();

			String info = timeInfo + "\n" + classInfo + "\n" + methInfo;
			// 将日志保存到指定位置或做输出
			System.out.println(info);

			// 继续将异常抛出
			throw e;
		}
		System.out.println("环绕通知=======在原方法执行之后执行");
	}

}
