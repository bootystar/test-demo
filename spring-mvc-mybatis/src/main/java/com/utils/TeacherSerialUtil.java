package com.utils;

public class TeacherSerialUtil {
    
    public static String createTeacherSerial(int num){
        String serial=num+"";
        //生成教师编号,总共4位,传入数字不够时在前方拼接0
        for (int i = 0; i <4-serial.length() ; i++) {
            serial=0+serial;
        }
        return "tea"+serial;
    }
}
