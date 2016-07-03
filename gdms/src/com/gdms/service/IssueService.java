package com.gdms.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.IssueMapper;
import com.gdms.pojo.Issue;
import com.gdms.pojo.User;

@Service("issueService")
public class IssueService {
	@Resource
	private IssueMapper issueDAO;

	public IssueMapper getIssueDAO() {
		return issueDAO;
	}

	public void setIssueDAO(IssueMapper issueDAO) {
		this.issueDAO = issueDAO;
	}
	
	public Issue getContent(int issueId){
		return issueDAO.selectByPrimaryKey(issueId);
	}
	
	public List<Issue> getIssueListByTeacher(int teacherId)
	{
		/**
		 * ��ȡ��ʦ�Ŀ���
		 */
		return issueDAO.selectListByTeacherId(teacherId);
	}
	
	public void createIssue(Issue issue)
	{
		/**
		 * ��������
		 */
		issueDAO.insert(issue);
	}
	
	public Issue getStudentIssue(User student){
		return issueDAO.selectByStudentId(student.getId());
	}
}
