package com.example.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author booty
 * @since 2023/12/26
 */
@Data
public class Child {
    private String name;
    private Integer age;
    private Date birthDay;
    private List<String> text; //数组
}