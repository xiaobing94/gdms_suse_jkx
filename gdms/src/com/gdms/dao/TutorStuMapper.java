package com.gdms.dao;

import java.util.List;

import com.gdms.pojo.TutorStu;

public interface TutorStuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TutorStu record);

    int insertSelective(TutorStu record);

    TutorStu selectByPrimaryKey(Integer id);
    
    TutorStu getTeacherByStudentId(Integer studentId);
    
    int selectChoiseCount(Integer teacher_id, Integer grade);

    int updateByPrimaryKeySelective(TutorStu record);

    int updateByPrimaryKey(TutorStu record);
    
    List<TutorStu> getTutorStuByMajor(String major, Integer grade);
    
    List<TutorStu> getAllTutorStu(Integer grade);
}