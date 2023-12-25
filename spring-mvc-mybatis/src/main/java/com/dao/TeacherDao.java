package com.dao;

import com.bean.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDao {

    @Insert("insert into teacher values (default,#{pwd},#{name},#{serial},#{gender},#{birthday},#{workday},#{jid},#{pid},#{nat},#{status})")
    int insertTeacher(Teacher teacher);


    @Update("update teacher set status=#{status} address=#{address} where id=#{id}")
    int updateTeacher(Teacher teacher);


    @Delete("delete from teacher where id = #{id}")
    int deleteTeacher(Teacher teacher);


    @Select("select * from teacher")
    List<Teacher> selectTeacher(Teacher teacher);

    @Select("select count(id) from teacher")
    int selectTeacherCount();


}
