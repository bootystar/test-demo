package com.java.service;

import com.java.bean.User;

import lombok.Data;

@Data
public interface UserService {
	public int add(User user);

}
