package com.gdms.dao;

import com.gdms.pojo.StuTutor;

public interface StuTutorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StuTutor record);

    int insertSelective(StuTutor record);

    StuTutor selectByPrimaryKey(Integer id);
    
    StuTutor selectByStudentId(Integer studentId);
    
    int selectChoiseCountByTeacherId(Integer teacher_id, Integer grade);

    int updateByPrimaryKeySelective(StuTutor record);

    int updateByPrimaryKey(StuTutor record);
}