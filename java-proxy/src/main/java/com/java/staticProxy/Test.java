package com.java.staticProxy;

import com.sun.org.apache.bcel.internal.generic.NEW;

//公共接口
interface BankService{
	void transfer();
}

//被代理类
class BankServiceImpl implements BankService{
	@Override
	public void transfer() {
		System.out.println("加钱");
		System.out.println("减钱");
	}
}

//代理类
class BankServicProxy implements BankService{
	BankService bankService;
	
	@Override
	public void transfer() {
		System.out.println("开启事务");
		bankService.transfer();
		System.out.println("提交/回滚事务");
	}

	public BankService getBankService() {
		return bankService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public BankServicProxy() {
		super();
	}

	public BankServicProxy(BankService bankService) {
		super();
		this.bankService = bankService;
	}

}


//测试
public class Test {
	public static void main(String[] args) {
		BankService bankService=new BankServicProxy(new BankServiceImpl());
		bankService.transfer();
	}
}
