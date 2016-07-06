package com.gdms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdms.pojo.User;
import com.gdms.service.TutorStuService;

@Controller
@RequestMapping("")
public class IndexController {
	@Resource
	private TutorStuService tutorStuService;

	public TutorStuService getTutorStuService() {
		return tutorStuService;
	}

	public void setTutorStuService(TutorStuService tutorStuService) {
		this.tutorStuService = tutorStuService;
	}

	@RequestMapping("")
	public String index(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user.isStudent()) {
			User teacher = tutorStuService.getTeacherByStudent(user.getId());
			model.addAttribute("teacher", teacher);
		}
		model.addAttribute("user", user);
		return "index";
	}

	@RequestMapping("/")
	public String indexTmp(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user.isStudent()) {
			User teacher = tutorStuService.getTeacherByStudent(user.getId());
			model.addAttribute("teacher", teacher);
		}
		model.addAttribute("user", user);
		return "index";
	}

	@RequestMapping("/test1")
	public String test(Model model) {
		return "gd/as-teacher-list";
	}
}
