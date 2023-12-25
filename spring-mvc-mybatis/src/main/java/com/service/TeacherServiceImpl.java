package com.service;

import com.bean.Teacher;
import com.dao.TeacherDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.utils.PageResult;
import com.utils.TeacherSerialUtil;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    @Transactional
    @Override
    public int insertTeacher(Teacher teacher) {
        //获取教师的数量
        int num = teacherDao.selectTeacherCount();
        //生成新建教师的编号
        String serial = TeacherSerialUtil.createTeacherSerial(num);
        teacher.setSerial(serial);
        int i = teacherDao.insertTeacher(teacher);
        return i;
    }

    @Transactional
    @Override
    public boolean deleteTeacher(Teacher teacher) {
        int i = teacherDao.deleteTeacher(teacher);
        return i > 0 ? true : false;
    }

    @Transactional
    @Override
    public boolean updateTeacher(Teacher teacher) {
        int i = teacherDao.updateTeacher(teacher);
        return i > 0 ? true : false;
    }

    @Override
    public PageResult<List<Teacher>> selectTeacherByPage(int page, int size) {
        PageResult<List<Teacher>> result = new PageResult<>();
        Page<Teacher> startPage = PageHelper.startPage(page, size);
        List<Teacher> teachers = teacherDao.selectTeacher(new Teacher());
        result.setData(teachers);
        result.setCurrentPage(page);
        result.setSize(size);
        result.setTotal(startPage.getTotal());
        result.setTotalPage(startPage.getPages());
        return result;
    }


}
