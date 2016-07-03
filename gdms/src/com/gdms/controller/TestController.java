package com.gdms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdms.pojo.User;

@Controller
@RequestMapping("test")
public class TestController {
	@RequestMapping("/test")
	public String register(String test, Model model){
		return test;
	}
	@RequestMapping("/prompt")
	public String prompt(String info, HttpServletRequest request, Model model){
		model.addAttribute("msg", info);
		return "notice-msg";
	}
}
