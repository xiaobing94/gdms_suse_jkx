package com.gdms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdms.pojo.Notice;
import com.gdms.pojo.User;
import com.gdms.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Resource
	private NoticeService noticeService;

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@RequestMapping("/add-notice")
	public String create(Notice notice, HttpSession session,
			HttpServletRequest request, Model model) {
		User user = (User) session.getAttribute("user");
		String method = request.getMethod();
		if ("GET".equals(method)) {
			model.addAttribute("user", user);
			return "notice/add-notice";
		}
		if (!user.isStudent()) {
			noticeService.addNotice(notice, user);
			//model.addAttribute("msg", "发布成功");
			return "redirect:notice-list";
		} else {
			model.addAttribute("msg", "你没有对应的发布权限");
			return "notice/add-notice";
		}

	}

	@RequestMapping("/notice-list")
	public String getList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		// List<Notice> teacherNoticeList =
		// noticeService.getNoticeListByTeacherId(teacher_id);
		if (user.isStudent()) {
			List<Notice> teacherNoticeList = noticeService
					.getTutorNoticeList(user);
			model.addAttribute("teacherNoticeList", teacherNoticeList);
		} else if (user.haveTeacherPermission()) {
			List<Notice> teacherNoticeList = noticeService
					.getNoticeListByTeacherId(user.getId());
			model.addAttribute("teacherNoticeList", teacherNoticeList);
		}
		if (user.isStudent()||user.haveDirectorPermission()||user.haveTeacherPermission()) {
			List<Notice> majorNoticeList = noticeService
					.getMajorNoticeList(user.getMajor());
			model.addAttribute("majorNoticeList", majorNoticeList);
		}

		List<Notice> collegeNoticeList = noticeService.getCollegeNoticeList();
		model.addAttribute("collegeNoticeList", collegeNoticeList);
		return "notice/notice-list";
	}

	@RequestMapping("/detail")
	public String getContent(int notice_id, HttpSession session, Model model) {
		// User user = (User)session.getAttribute("user");
		// System.out.println("service:"+noticeService);
		Notice notice = noticeService.getNoticeById(notice_id);
		// System.out.println("notice"+notice);
		model.addAttribute("notice", notice);
		return "notice/notice-detail";
	}
	@RequestMapping("/del-notice-list")
	public String delNoticeList(HttpSession session, Model model) {
		User user = (User)session.getAttribute("user");
		if(user.haveLeaderPermission()){
			List<Notice> noticeList = noticeService.getAllNotice();
			model.addAttribute("noticeList", noticeList);
		}else if(user.haveLeaderPermission()){
			List<Notice> noticeList = noticeService.getAllNoticeByMajor(user.getMajor());
			model.addAttribute("noticeList", noticeList);
		}else if(user.haveTeacherPermission()){
			List<Notice> noticeList = noticeService.getNoticeListWithAuthorUserByTeacherId(user.getId());
			model.addAttribute("noticeList", noticeList);
		}
		return "notice/notice-list-for-del";
	}
	@RequestMapping("/del")
	public String delNotice(int notice_id[], Model model) {
		noticeService.deleteByArr(notice_id);
		model.addAttribute("msg", "删除成功");
		return "notice-msg";
	}
}
