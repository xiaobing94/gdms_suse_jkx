package com.gdms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.IssueMapper;
import com.gdms.dao.TutorStuMapper;
import com.gdms.dao.UserMapper;
import com.gdms.pojo.Issue;
import com.gdms.pojo.TutorStu;
import com.gdms.pojo.User;
import com.gdms.tools.PropertiesLog4j;
import com.gdms.tools.UtilXls;

@Service("tutorService")
public class TutorStuService {
	@Resource
	private UserMapper userDAO;
	@Resource
	private TutorStuMapper tutorStuDAO;
	@Resource
	private IssueMapper issueDAO;

	public IssueMapper getIssueDAO() {
		return issueDAO;
	}

	public void setIssueDAO(IssueMapper issueDAO) {
		this.issueDAO = issueDAO;
	}

	public UserMapper getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserMapper userDAO) {
		this.userDAO = userDAO;
	}

	public TutorStuMapper getTutorStuDAO() {
		return tutorStuDAO;
	}

	public void setTutorStuDAO(TutorStuMapper tutorStuDAO) {
		this.tutorStuDAO = tutorStuDAO;
	}

	public boolean create(int studentId, int teathcerId) {
		User teacher = userDAO.selectByPrimaryKey(teathcerId);
		User student = userDAO.selectByPrimaryKey(studentId);
		int grade = PropertiesLog4j.getGrade();
		if (teacher == null || student == null) {
			return false;
		} else {
			if (teacher.getAmount() == null
					|| tutorStuDAO.selectChoiseCount(teathcerId, grade) >= teacher
							.getAmount()) {
				return false;
			}
			TutorStu tutorStu = new TutorStu();
			tutorStu.setStudentId(studentId);
			tutorStu.setTeathcerId(teathcerId);
			tutorStuDAO.insertSelective(tutorStu);
			return true;
		}
	}

	public List<User> getLastStudentByMajor(String major) {
		/**
		 * 获取没有导师选择的学生，剩下的学生
		 */
//		PropertiesLog4j p4j = new PropertiesLog4j();
		int grade = PropertiesLog4j.getGrade();
		return userDAO.getLastStudentByMajor(major, grade);
	}

	public void distributeStudent(int[] studentId, int teacherId) {
		for (int i = 0; i < studentId.length; i++) {
			create(studentId[i], teacherId);
		}
	}

	public User getTeacherByStudent(int studentId) {
		/**
		 * 获取老师，根据学生id
		 */
		TutorStu tutorStu = tutorStuDAO.getTeacherByStudentId(studentId);
		tutorStu.setInstance(userDAO.selectByPrimaryKey(tutorStu.getStudentId()), userDAO.selectByPrimaryKey(tutorStu.getTeacherId()));
		return tutorStu.getTeacher();
	}

	public Map<String, List> getDataByMajor(String major, int grade) {
		/**
		 * 获取专业数据map对象
		 */
		Map<String, List> map = new HashMap<String, List>();
		List<String> teacherList = new ArrayList<String>();
		List<String> studentList = new ArrayList<String>();
		List<String> issueList = new ArrayList<String>();
		List<TutorStu> tutorStuList = tutorStuDAO.getTutorStuByMajor(major,
				grade);
		for (int i = 0; i < tutorStuList.size(); i++) {
			TutorStu tutorStu = tutorStuList.get(i);
			tutorStu.setInstance(userDAO.selectByPrimaryKey(tutorStu.getStudentId()), userDAO.selectByPrimaryKey(tutorStu.getTeacherId()));
			User teacher = tutorStu.getTeacher();
			User student = tutorStu.getStudent();
			teacherList.add(teacher.getName());
			studentList.add(student.getName());
			int issueId = student.getIssueId() == null? 0:student.getIssueId();
			Issue issue;
			if (issueId != 0) {
				issue = issueDAO.selectByPrimaryKey(issueId);
				issueList.add(issue.getSubject());
			} else {
				issueList.add(null);
			}
		}
		map.put("学生", studentList);
		map.put("老师", teacherList);
		map.put("课题", issueList);
		return map;
	}

	public Map<String, List> getAllData(int grade) {
		/**
		 * 获取所有数据map对象
		 */
		Map<String, List> map = new HashMap<String, List>();
		List<String> teacherList = new ArrayList<String>();
		List<String> studentList = new ArrayList<String>();
		List<String> issueList = new ArrayList<String>();
		List<TutorStu> tutorStuList = tutorStuDAO.getAllTutorStu(grade);
		for (int i = 0; i < tutorStuList.size(); i++) {
			TutorStu tutorStu = tutorStuList.get(i);
			tutorStu.setInstance(userDAO.selectByPrimaryKey(tutorStu.getStudentId()), userDAO.selectByPrimaryKey(tutorStu.getTeacherId()));
			User teacher = tutorStu.getTeacher();
			User student = tutorStu.getStudent();
			teacherList.add(teacher.getName());
			studentList.add(student.getName());
			int issueId = student.getIssueId();
			Issue issue;
			if (issueId != 0) {
				issue = issueDAO.selectByPrimaryKey(issueId);
				issueList.add(issue.getSubject());
			} else {
				issueList.add(null);
			}
		}
		map.put("学生", studentList);
		map.put("老师", teacherList);
		map.put("课题", issueList);
		return map;
	}

	public void exportMajorXls(String major, String filePathAndName) {
		/**
		 * 获得本专业对应年级的xls结果
		 */
		int grade = PropertiesLog4j.getGrade();
		Map<String, List> map = getDataByMajor(major, grade);
		UtilXls.WriteXls(filePathAndName, map);
//		return null;
	}
	
	public void exportAllXls(String filePathAndName) {
		/**
		 * 获得对应年级的xls结果
		 */
		int grade = PropertiesLog4j.getGrade();
		Map<String, List> map = getAllData(grade);
		UtilXls.WriteXls(filePathAndName, map);
	}
}
