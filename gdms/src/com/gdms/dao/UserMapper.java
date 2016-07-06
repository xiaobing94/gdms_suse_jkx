package com.gdms.dao;

import java.util.List;

import com.gdms.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);
    
    User selectByWorkId(String work_id);
    
    List<User> selectTeacherByMajor(String major);
    
    List<User> selectChoiseMeStudent(Integer teacher_id, Integer grade);  //获取我选择的学生列表
    
    List<User> selectStudentByTeacherId(Integer teacher_id, Integer grade);  //获取我的学生列表
    
    User selectByWorkIdandPassword(String work_id, String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> getLastStudentByMajor(String major, Integer grade);
}