package com.utils;


import com.enums.StateEnum;

public class ResponseResult<T> {
    private int code;
    private StateEnum state;
    private String message;
    private T data;


    public void getResult(boolean b) {
        if (b) {
            this.code = 200;
            this.state = StateEnum.SUCCESS;
            this.message = "操作成功";
        } else {
            this.code = 500;
            this.state = StateEnum.FAIL;
            this.message = "操作失败";
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
