package com.java.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//公共接口
interface BankService {
	void transfer();
}

//被代理类
class BankServiceImpl implements BankService {
	@Override
	public void transfer() {
		System.out.println("加钱");
		System.out.println("减钱");
	}
}

//JDK动态代理工厂
class JdkProxyFactory {
	private Object target;

	public Object getProxyInstance() {
		//参数1.类加载器
		ClassLoader classLoader = this.getClass().getClassLoader();
		//参数2:被代理类实现的所有接口
		Class<?>[] interfaces = target.getClass().getInterfaces();
		//参数3:调用的处理程序
		InvocationHandler handler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println("开启事务");
				Object result = method.invoke(target, args);
				System.out.println("提交事务");
				return result;
			}
		};
		//获取生成的代理类
		Object proxyInstance = Proxy.newProxyInstance(classLoader, interfaces, handler);
		return proxyInstance;
	}

	public JdkProxyFactory(Object target) {
		super();
		this.target = target;
	}

	public JdkProxyFactory() {
		super();
	}

}

public class Test {
	public static void main(String[] args) {
		// 创建工厂
		JdkProxyFactory factory = new JdkProxyFactory(new BankServiceImpl());
		// 获取代理类对象
		Object proxyObject = factory.getProxyInstance();
		System.out.println(proxyObject);
		System.out.println(proxyObject.getClass());
//		判断生成的类型是否是指定接口的子类,如果是则使用方法调用功能
		if (proxyObject instanceof BankService) {
			BankService bankService = (BankService) proxyObject;
			bankService.transfer();
		}
	}

}
