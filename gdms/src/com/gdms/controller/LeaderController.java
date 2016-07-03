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
		 * 设置开始时间
		 */
//		PropertiesLog4j p4j = new PropertiesLog4j();
//		Properties pps = p4j.getProperties();
//		//String s = DateFormat.getDateInstance().format(date);
//		System.out.println("时间:"+dateStr);
//		pps.setProperty("startDate", dateStr);
		PropertiesLog4j.setStartDate(date);
		model.addAttribute("msg", "开始时间设置为:"+date);
		return "notice-msg";
	}
	@RequestMapping(value="setStart", method=RequestMethod.GET)
	public String setStartDate(){
		return "dean/set-choose-teacher-time";
	}
	@RequestMapping(value="setGrade", method=RequestMethod.POST)
	public String setCurrentGrade(int grade, Model model){
		/**
		 * 设置当前进行选导师的年级
		 */
		PropertiesLog4j.setGrade(grade);
		model.addAttribute("msg", "年级设置为:"+grade);
		return "notice-msg";
	}
	@RequestMapping(value="setGrade", method=RequestMethod.GET)
	public String setCurrentGrade(){
		/**
		 * 设置当前进行选导师的年级的视图
		 */
		return "dean/set-grade";
	}
}
