package com.gdms.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.CommentMapper;
import com.gdms.dao.DegreeMapper;
import com.gdms.pojo.Comment;
import com.gdms.pojo.Degree;
import com.gdms.pojo.User;

@Service("degreeService")
public class DegreeService {
	@Resource
	private DegreeMapper degreeDAO;
	@Resource
	private CommentMapper commentDAO;

	public CommentMapper getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentMapper commentDAO) {
		this.commentDAO = commentDAO;
	}

	public DegreeMapper getDegreeDAO() {
		return degreeDAO;
	}

	public void setDegreeDAO(DegreeMapper degreeDAO) {
		this.degreeDAO = degreeDAO;
	}
	public void create(Degree degree){
		/**
		 * ����׶����
		 */
		Date date = new Date();
		degree.setDateline(date);
		degreeDAO.insertSelective(degree);
	}
	public void commentDegree(Comment comment, User teacher){
		/**
		 * ���۽׶����
		 */
		comment.setDateline(new Date());
		//comment.setDegreeId(degreeId);
		comment.setTeacherId(teacher.getId());
		commentDAO.insertSelective(comment);
	}
	public List<Degree> getDegreeListByStudentId(int studentId){
		/**
		 * ����ѧ��id��ȡ�׶�����б�
		 */
		
		return degreeDAO.selectByStudentId(studentId);
	}
	public List<Comment> getCommentListByDegreeId(int degreeId){
		/**
		 * ����ѧ��id��ȡ�׶�����б�
		 */
		
		return commentDAO.selectByDegreeId(degreeId);
	}
}
