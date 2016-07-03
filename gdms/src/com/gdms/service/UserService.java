package com.gdms.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import jxl.read.biff.BiffException;

import org.springframework.stereotype.Service;

import com.gdms.dao.UserMapper;
import com.gdms.pojo.User;
import com.gdms.tools.PropertiesLog4j;
import com.gdms.tools.UtilXls;
import com.gdms.tools.ValidateUser;

@Service("userService")
public class UserService {
	@Resource
	private UserMapper userDAO;

	public User getUser(int id) {
		return userDAO.selectByPrimaryKey(id);
	}

	// ��½
	public boolean login(HttpSession session, String workId, String password) {
		// TODO Auto-generated method stub
		// String passwdMd5 = Md5Util.Convert2Md5(password);
		User user = userDAO.selectByWorkIdandPassword(workId, password);
		if (user != null) {
			session.setAttribute("user", user);
			return true;
		}
		return false;
	}

	// ע��
	public void logout(HttpSession session) {
		session.removeAttribute("user");
	}

	// ע��
	public boolean register(User user, String validateCode) {
		User user_tmp = userDAO.selectByWorkId(user.getWorkId());
		if (user_tmp != null) {
			return false;
		} else {
			if (ValidateUser.Validate(user, validateCode)) {
				userDAO.insertSelective(user);
				return true;
			} else {
				return false;
			}
		}
	}

	public List<User> getTeacherByMajor(String major) {
		List<User> teacherList = userDAO.selectTeacherByMajor(major);
		return teacherList;
	}

	public List<User> getStudentChoiseMeByTeacher(User teacher) {
		int grade = PropertiesLog4j.getGrade();
		return userDAO.selectChoiseMeStudent(teacher.getId(), grade);
	}
	
	public List<User> getStudentByTeacher(User teacher) {
		int grade = PropertiesLog4j.getGrade();
		return userDAO.selectStudentByTeacherId(teacher.getId(), grade);
	}
	
	public void updateUserById(User user) {
		this.userDAO.updateByPrimaryKeySelective(user);
	}

	public User[] getTeacherUserArrByXls(File xlsFile) throws BiffException,
			IOException {
		/**
		 * ��xls��ʦ����ת��ΪUser��������
		 */
		Map<String, String[]> map = UtilXls.getDataByFilePath(xlsFile);
		int length = 0;
		for (String[] data : map.values()) {
			length = data.length;
			break;
		}
		User[] userArr = new User[length];
		// int i=0;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String header = entry.getKey();
			String[] values = entry.getValue();
			if (header.equals("id")) {
				for (int i = 0; i < length; i++) {
					userArr[i] = new User();
					userArr[i].setWorkId(values[i]);
					userArr[i].setType(User.TEACHER);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setName(values[i]);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setPassword(values[i]);
				}
			}
			if (header.equals("רҵ")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setMajor(values[i]);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setIntroduction(values[i]);
				}
			}
			if (header.equals("ְ��")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setJobtitle(values[i]);
				}
			}
			if (header.equals("ѧ������")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setAmount(Integer.parseInt(values[i]));
				}
			}
		}
		return userArr;
	}

	public User[] getStudentUserArrByXls(File xlsFile) throws BiffException,
			IOException {
		/**
		 * ��xlsѧ������ת��ΪUser��������
		 */
		Map<String, String[]> map = UtilXls.getDataByFilePath(xlsFile);
		int length = 0;
		for (String[] data : map.values()) {
			length = data.length;
			break;
		}
		User[] userArr = new User[length];
		// int i=0;
		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String header = entry.getKey();
			String[] values = entry.getValue();
			if (header.equals("id")) {
				for (int i = 0; i < length; i++) {
					userArr[i] = new User();
					userArr[i].setWorkId(values[i]);
					userArr[i].setType(8);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setName(values[i]);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setPassword(values[i]);
				}
			}
			if (header.equals("רҵ")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setMajor(values[i]);
				}
			}
			if (header.equals("����")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setIntroduction(values[i]);
				}
			}
			if (header.equals("�꼶")) {
				for (int i = 0; i < length; i++) {
					userArr[i].setTotal(Integer.parseInt(values[i]));
				}
			}
		}
		return userArr;
	}

	public void createUserByArr(User[] userArr) {
		/**
		 * ��������������ݱ��浽���ݿ�
		 */
		for (int i = 0; i < userArr.length; i++) {
			if (userDAO.selectByWorkId(userArr[i].getWorkId()) == null) {
				userDAO.insertSelective(userArr[i]);
			}
		}
	}

	public void importTeacherXls(File xlsFile) {
		/**
		 * ����xls��ʦ���ݵ����ݿ�
		 */
		User[] userArr = null;
		try {
			userArr = getTeacherUserArrByXls(xlsFile);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createUserByArr(userArr);
	}

	public void importStudentXls(File xlsFile) {
		/**
		 * ����xlsѧ�����ݵ����ݿ�
		 */
		User[] userArr = null;
		try {
			userArr = getStudentUserArrByXls(xlsFile);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createUserByArr(userArr);
	}
}
