package com.example;

import com.example.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author booty
 * @since 2023/12/26
 */
@SpringBootTest
@Slf4j
public class Test1 {

    @Autowired
    private Person person;

    @Test
    public void test1(){
        System.out.println(person);
    }


    @Test
    public void test2(){
        for (int i = 0; i < 1000; i++) {
            log.info("this is a log info loopCount:{}",i);
        }
    }
}
