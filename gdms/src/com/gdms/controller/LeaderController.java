package com.gdms.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdms.tools.PropertiesLog4j;

@Controller
@RequestMapping("leader")
public class LeaderController {
	@RequestMapping(value="setStart", method=RequestMethod.POST)
	public String setStartDate(String date, Model model){
		/**
		 * ���ÿ�ʼʱ��
		 */
//		PropertiesLog4j p4j = new PropertiesLog4j();
//		Properties pps = p4j.getProperties();
//		//String s = DateFormat.getDateInstance().format(date);
//		System.out.println("ʱ��:"+dateStr);
//		pps.setProperty("startDate", dateStr);
		PropertiesLog4j.setStartDate(date);
		model.addAttribute("msg", "��ʼʱ������Ϊ:"+date);
		return "notice-msg";
	}
	@RequestMapping(value="setStart", method=RequestMethod.GET)
	public String setStartDate(){
		return "dean/set-choose-teacher-time";
	}
	@RequestMapping(value="setGrade", method=RequestMethod.POST)
	public String setCurrentGrade(int grade, Model model){
		/**
		 * ���õ�ǰ����ѡ��ʦ���꼶
		 */
		PropertiesLog4j.setGrade(grade);
		model.addAttribute("msg", "�꼶����Ϊ:"+grade);
		return "notice-msg";
	}
	@RequestMapping(value="setGrade", method=RequestMethod.GET)
	public String setCurrentGrade(){
		/**
		 * ���õ�ǰ����ѡ��ʦ���꼶����ͼ
		 */
		return "dean/set-grade";
	}
}
