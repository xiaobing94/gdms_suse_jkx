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
			error = "�㲻����ʦ";break;
		case DIRECTOR:
			error = "�㲻��ϵ����";break;
		case STUDENT:
			error = "�㲻��ѧ��";break;
		case LEADER:
			error = "�㲻��Ժ�쵼";break;
		case NOT_STUDENT:
			error = "ѧ��û�����Ȩ��";break;
		case START:
			error = "��û�п�ʼѡ��ʦ";break;
		default:
				error="δ֪����";
		}
		model.addAttribute("msg", error);
		return "notice-msg";
	}

}
