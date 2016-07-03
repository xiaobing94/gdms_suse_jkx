package com.gdms.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.StuTutorMapper;
import com.gdms.dao.UserMapper;
import com.gdms.pojo.StuTutor;
import com.gdms.pojo.User;
import com.gdms.tools.PropertiesLog4j;

@Service("stuTutorService")
public class StuTutorService {
	@Resource
	private StuTutorMapper stuTutorDAO;
	@Resource
	private UserMapper userDAO;

	public UserMapper getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserMapper userDAO) {
		this.userDAO = userDAO;
	}

	public StuTutorMapper getStuTutorDAO() {
		return stuTutorDAO;
	}

	public void setStuTutorDAO(StuTutorMapper stuTutorDAO) {
		this.stuTutorDAO = stuTutorDAO;
	}

	public boolean create(int teacherId, User student) {
		StuTutor stuTutor = new StuTutor();
		User teacher = userDAO.selectByPrimaryKey(teacherId);
		int grade = PropertiesLog4j.getGrade();
		int count = stuTutorDAO.selectChoiseCountByTeacherId(teacher.getId(),
				grade);
		stuTutor.setStudentId(student.getId());
		stuTutor.setTeacherId(teacherId);
		StuTutor oldStuTutor = stuTutorDAO.selectByStudentId(student.getId());
		if (oldStuTutor == null) {
			if (teacher.getAmount() != null && count < teacher.getAmount()) {
				stuTutorDAO.insertSelective(stuTutor);
				return true;
			} else {
				return false;
			}
		}else{
			stuTutor.setId(oldStuTutor.getId());
			stuTutorDAO.updateByPrimaryKeySelective(stuTutor);
			return true;
		}
	}

	public int getAmountByTeacherId(int teacher_id) {
		int grade = PropertiesLog4j.getGrade();
		return stuTutorDAO.selectChoiseCountByTeacherId(teacher_id, grade);
	}
}
