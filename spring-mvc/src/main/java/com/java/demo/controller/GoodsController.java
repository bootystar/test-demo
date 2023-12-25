package com.java.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;


//对应文件为spring-mvc2,实现使用了不同的接口
public class GoodsController implements HttpRequestHandler{

	@Override
	public void handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		System.out.println("处理请求");
	}

}
