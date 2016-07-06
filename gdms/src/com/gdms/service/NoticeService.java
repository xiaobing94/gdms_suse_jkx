package com.gdms.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.NoticeMapper;
import com.gdms.dao.TutorStuMapper;
import com.gdms.dao.UserMapper;
import com.gdms.pojo.Message;
import com.gdms.pojo.Notice;
import com.gdms.pojo.TutorStu;
import com.gdms.pojo.User;

@Service("noticeService")
public class NoticeService {
	@Resource
	private NoticeMapper noticeDAO;
	@Resource
	private UserMapper userDAO;
	@Resource
	private TutorStuMapper tutorStuDAO;

	public TutorStuMapper getTutorStuDAO() {
		return tutorStuDAO;
	}

	public void setTutorStuDAO(TutorStuMapper tutorStuDAO) {
		this.tutorStuDAO = tutorStuDAO;
	}

	public UserMapper getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserMapper userDAO) {
		this.userDAO = userDAO;
	}

	public NoticeMapper getNoticeDAO() {
		return noticeDAO;
	}

	public void setNoticeDAO(NoticeMapper noticeDAO) {
		this.noticeDAO = noticeDAO;
	}

	public void addNotice(Notice notice, User user) {
		notice.setAuthor(user.getId());
		notice.setDateline(new Date());
		noticeDAO.insertSelective(notice);
	}

	public List<Notice> getNoticeListByTeacherId(int teacher_id) {
		List<Notice> noticeList = noticeDAO.selectByTeacherId(teacher_id);
		return noticeList;
	}

	public Notice getNoticeById(int id) {
		return noticeDAO.selectByPrimaryKey(id);
	}

	public void updateContent(Notice notice, User user) {
		user.setIssueId(notice.getId());
		userDAO.updateByPrimaryKey(user);
	}

	public boolean create(Notice notice) {
		return false;
	}

	public List<Notice> getCollegeNoticeList() {
		List<Notice> noticeList = noticeDAO.selectCollage();
//		if(noticeList==null){
//			return null;
//		}
//		Collections.sort(noticeList, new Comparator<Notice>() {
//
//			@Override
//			public int compare(Notice notice1, Notice notice2) {
//				// TODO Auto-generated method stub
//				return notice2.getDateline().compareTo(notice1.getDateline());
//			}
//        });
		return noticeList;
	}
	
	public void deleteById(int id) {
		noticeDAO.deleteByPrimaryKey(id);
	}
	
	public void deleteByArr(int[] noticeIdArr){
		/**
		 * 根据文章id数组删除文章
		 */
		for(int id: noticeIdArr){
			deleteById(id);
		}
	}
	
	public List<Notice> getMajorNoticeList(String major) {
		List<Notice> noticeList = noticeDAO.selectByMajor(major);
//		if(noticeList==null){
//			return null;
//		}
//		Collections.sort(noticeList, new Comparator<Notice>() {
//
//			@Override
//			public int compare(Notice notice1, Notice notice2) {
//				// TODO Auto-generated method stub
//				return notice2.getDateline().compareTo(notice1.getDateline());
//			}
//        });
		return noticeList;
	}

	public List<Notice> getTutorNoticeList(User student) {
		TutorStu tutorStu = tutorStuDAO.getTeacherByStudentId(student.getId());
		if (tutorStu != null) {
			List<Notice> noticeList = noticeDAO.selectByTeacherId(tutorStu.getTeacherId());
			//User teacher = userDAO.selectByPrimaryKey(tutorStu.getTeacherId());
//			Collections.sort(noticeList, new Comparator<Notice>() {
//
//				@Override
//				public int compare(Notice notice1, Notice notice2) {
//					// TODO Auto-generated method stub
//					return notice2.getDateline().compareTo(notice1.getDateline());
//				}
//	        });
			return noticeList;
		} else {
			return null;
		}
	}
	public List<Notice> getAllNotice() {
		List<Notice> noticeList = noticeDAO.selectAll();
		for(Notice notice:noticeList){
			Integer authorId = notice.getAuthor();
			User authorUser = null;
			if(authorId!=null){
				authorUser= userDAO.selectByPrimaryKey(authorId);
			}
			notice.setAuthorUser(authorUser);
		}
		return noticeList;
	}
	public List<Notice> getAllNoticeByMajor(String major) {
		List<Notice> noticeList = noticeDAO.selectAllByMajor(major);
		for(Notice notice:noticeList){
			Integer authorId = notice.getAuthor();
			User authorUser = null;
			if(authorId!=null){
				authorUser= userDAO.selectByPrimaryKey(authorId);
			}
			notice.setAuthorUser(authorUser);
		}
		return noticeList;
	}
	public List<Notice> getNoticeListWithAuthorUserByTeacherId(int teacher_id){
		List<Notice> noticeList = getNoticeListByTeacherId(teacher_id);
		for(Notice notice:noticeList){
			Integer authorId = notice.getAuthor();
			User authorUser = null;
			if(authorId!=null){
				authorUser= userDAO.selectByPrimaryKey(authorId);
			}
			notice.setAuthorUser(authorUser);
		}
		return noticeList;
	}
}
