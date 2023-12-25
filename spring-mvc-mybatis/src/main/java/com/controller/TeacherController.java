package com.controller;

import com.bean.Teacher;
import com.service.TeacherService;
import com.utils.PageResult;
import com.utils.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;


    @RequestMapping("/insert")
    @ResponseBody
    public String insertTeacher(Teacher teacher) {
        int i = teacherService.insertTeacher(teacher);
        return "受影响的行数:" + i;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseResult<String> update(Teacher teacher) {
        ResponseResult responseResult = new ResponseResult();
        boolean b = teacherService.updateTeacher(teacher);
        responseResult.getResult(b);
        return responseResult;
    }

    @RequestMapping("/selectTeacherByPage")
    @ResponseBody
    public PageResult<List<Teacher>> selectTeacherByPage(int page,int size){
        return teacherService.selectTeacherByPage(page, size);
    }

    @ResponseBody
    @RequestMapping("delete")
    public ResponseResult deleteTeacher(Teacher teacher){
        ResponseResult result=new ResponseResult();
        boolean b = teacherService.deleteTeacher(teacher);
        result.getResult(b);
        return result;
    }



}
