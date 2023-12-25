package com.java.cglibProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


//被代理类(无接口)
class BankServiceImpl  {
	public void transfer() {
		System.out.println("加钱");
		System.out.println("减钱");
	}
}


class CglibProxyFactory{
	private Object target;

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public CglibProxyFactory() {
		super();
	}
	

	public CglibProxyFactory(Object target) {
		super();
		this.target = target;
	}
	

	public Object getProxyInstance() {
		//创建工具类对象,生成代理类对象
		Enhancer enhancer=new Enhancer();
		//指定代理的父类
		enhancer.setSuperclass(target.getClass());
		//设置处理器
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				System.out.println("开启事务");
				//执行原方法
				Object result =method.invoke(target, args);
				System.out.println("关闭事务");
				return result;
			}
		});
		
		return enhancer.create();
	}
	
}



public class Test {
	public static void main(String[] args) {
		CglibProxyFactory cglibProxyFactory=new CglibProxyFactory(new BankServiceImpl());
		Object proxy=cglibProxyFactory.getProxyInstance();
		System.out.println(proxy);
		
		
	}

}


