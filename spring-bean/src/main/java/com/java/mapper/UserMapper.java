package com.java.mapper;

import org.springframework.stereotype.Component;

import com.java.bean.User;

import lombok.Data;

/*
 * 使用Component注解开发 :
 *  在注解内可以使用value指定id(别名)
 *  不指定时默认以当前类名首字母小写作为id
 *  
 *使用注解后需要在spring的配置文件中开启注解扫描
 *
 */

@Component(value = "userMapper")
@Data
public interface UserMapper {
	int add(User user);

}
