package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author booty
 * @since 2023/12/26
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {


    @RequestMapping("/test")
    public String test(String name){
        log.trace(name);
        log.debug(name);
        log.info(name);
        log.warn(name);
        log.error(name);
        return name;
    }



}
