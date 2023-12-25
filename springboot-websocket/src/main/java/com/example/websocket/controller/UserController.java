package com.example.websocket.controller;

import com.example.websocket.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author booty
 * @date 2021/5/21 10:53
 */
@RestController
@RequestMapping("user")
public class UserController {

    @PostMapping("/regist")
    public String regist(@Validated User user, BindingResult result) {
        /*
        当请求到该方法时，标注了@Validated注解，那么springboot就会对user对象进行数据校验
        校验规则在User实体类的属性上指定，校验完毕之后会将结果封装到BindingResult对象中
        以参数的方式传入到该方法中
         */
        if (result.hasErrors()) {
            //获取所有的错误信息
            List<FieldError> errors = result.getFieldErrors();
            String errorMessage = "";
            for (FieldError error : errors) {
                errorMessage += error.getDefaultMessage();
                errorMessage += ";/n";
            }
            //如果有错误信息，可以向前端返回错误信息 JSON数据
            return errorMessage;
        }
        //登录验证逻辑

        //模拟直接成功
        return "success";
    }


}
