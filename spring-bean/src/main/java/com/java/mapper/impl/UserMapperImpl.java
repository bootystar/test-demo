package com.java.mapper.impl;

import org.springframework.stereotype.Repository;

import com.java.bean.User;
import com.java.mapper.UserMapper;

import lombok.Data;

@Repository
public class UserMapperImpl implements UserMapper{

	@Override
	public int add(User user) {
		System.out.println("UserMapper执行添加用户");
		return 0;
	}
	

}
