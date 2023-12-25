package com.controller;

import com.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

public class ExceptionHandleCenter {
    private static Logger logger = LoggerFactory.getLogger(ExceptionHandleCenter.class);



    @ExceptionHandler({ ArithmeticException.class, IOException.class })
    @ResponseBody
    public ResponseResult<Object> handleException(Exception e) {
        if (e instanceof ArithmeticException) {
            //返回指定结果
        }
        if (e instanceof IOException) {
            //返回另外的结果
        }
        logger.info(this.getClass()+" - "+e.getMessage());
        ResponseResult<Object> result = new ResponseResult<>();
        result.getResult(false);
        return result;
    }


}
