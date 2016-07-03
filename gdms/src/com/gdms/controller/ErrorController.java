package com.gdms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class ErrorController {
	public static final int TEACHER = 0;
	public static final int DIRECTOR = 1;
	public static final int STUDENT = 2;
	public static final int LEADER = 3;
	public static final int NOT_STUDENT = 4;
	public static final int START = 5;
	//public static final int START = 5;
	@RequestMapping("error")
	public String showError(int code, Model model){
		String error=null;
		switch(code){
		case TEACHER:
			error = "你不是老师";break;
		case DIRECTOR:
			error = "你不是系主任";break;
		case STUDENT:
			error = "你不是学生";break;
		case LEADER:
			error = "你不是院领导";break;
		case NOT_STUDENT:
			error = "学生没有这个权限";break;
		case START:
			error = "还没有开始选导师";break;
		default:
				error="未知错误";
		}
		model.addAttribute("msg", error);
		return "notice-msg";
	}

}
