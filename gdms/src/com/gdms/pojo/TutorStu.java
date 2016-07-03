package com.gdms.pojo;

import javax.annotation.Resource;

import com.gdms.dao.UserMapper;
import com.gdms.service.UserService;

public class TutorStu {
    private Integer id;

    private Integer studentId;

    private Integer teacherId;
    
    private User teacher;
    
    private User student;
    
//    @Resource
//    private UserService userService;
//    
//    public UserService getUserService() {
//		return userService;
//	}
//
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public User getTeacher() {
		return teacher;
	}

	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeathcerId() {
        return teacherId;
    }

    public void setTeathcerId(Integer teathcerId) {
        this.teacherId = teathcerId;
    }
    
    public void setInstance(User student, User teacher)
    {
    	if(studentId!=null&&teacherId!=null){
    		this.student = student;
    		this.teacher = teacher;
    	}
    }
}