package com.service;

import org.springframework.stereotype.Service;

//注解扫描
@Service
public class BankServiceImpl implements BankService {

	@Override
	public Integer transfer() {
		System.out.println("执行transfer方法模拟操作数据库取钱");
		return 1;
	}

	@Override
	public void save() {
		System.out.println("模拟操作数据库存钱");
	}

	@Override
	public void get(int money) {
		System.out.println("模拟数据库取钱");
		//用于手动触发异常
		System.out.println(1/money);
	}

}
