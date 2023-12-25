package com.service;


import com.bean.Teacher;
import com.utils.PageResult;

import java.util.List;

public interface TeacherService {

    int insertTeacher(Teacher teacher);

    boolean deleteTeacher(Teacher teacher);

    boolean updateTeacher(Teacher teacher);

    PageResult<List<Teacher>> selectTeacherByPage(int page,int size);

}
