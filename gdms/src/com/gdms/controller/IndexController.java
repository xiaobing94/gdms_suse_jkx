package com.gdms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdms.pojo.User;
@Controller
@RequestMapping("")
public class IndexController{
	@RequestMapping("")
	public String index(HttpSession session, Model model){
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "index";
	}
	@RequestMapping("/")
	public String indexTmp(HttpSession session, Model model){
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "index";
	}
	@RequestMapping("/test1")
	public String test(Model model){
		return "gd/as-teacher-list";
	}
}
