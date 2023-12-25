package com.example.websocket.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author booty
 * @date 2021/5/21 10:52
 */
@Data
public class User {


    @NotBlank(message = "账号不能为空")
    private String account;

    @Size(min = 6,max = 10,message = "密码必须在6-10位之间")
    private String pwd;

    @Email(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
            message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",
            message = "手机格式不正确")
    private String phone;
}
