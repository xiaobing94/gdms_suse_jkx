package com.gdms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdms.pojo.User;
import com.gdms.service.UserService;
import com.gdms.tools.MyHttpMethod;

@Controller
@RequestMapping("")
public class UserController {
	@Resource
	private UserService userService;

	@RequestMapping("/validatecode")
	public String getValidateCode(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> mapString = MyHttpMethod
				.MyGetSession("http://61.139.105.138/");
		String sessionIdString = mapString.get("sessionId");
		// HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("map", mapString);
		byte[] a = MyHttpMethod.MyGetYZM(
				"http://61.139.105.138/CheckCode.aspx", sessionIdString);
		// HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out;
		try {
			out = response.getOutputStream();
			out.write(a);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("/login")
	public String login(String work_id, String password, Model model,
			HttpSession session, HttpServletRequest request) {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			return "login";
		} else {
			if (userService.login(session, work_id, password)) {
				// System.out.println("user：");
				return "redirect:/";
			} else {
				// System.out.println("login false");
				return "login";
			}
		}
	}
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/login";
	}

	@RequestMapping("/student_register")
	public String studentRegister(User user, String validateCode,
			HttpServletRequest request, Model model) {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			return "register";
		} else {
			if (user == null) {
				model.addAttribute("msg", "user为空");
				return "register";
			} else if (user.getIntroduction() == null
					|| user.getMajor() == null || user.getPassword() == null
					|| user.getName() == null || user.getGrade() == null) {
				model.addAttribute("msg", "数据不能为空");
				// System.out.println("数据有问题");
				return "register";
			} else {
				if (userService.register(user, validateCode)) {
					model.addAttribute("user", user);
					// return "register";
					return "redirect:/login";
				} else {
					model.addAttribute("msg", "学号或密码错误");
					return "register";
				}
			}
		}
	}

	@RequestMapping("/teacher_register")
	public String teacherRegister(User user, String validateCode,
			HttpServletRequest request, Model model) {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			return "teacherregister";
		} else {
			if (user == null) {
				model.addAttribute("msg", "user为空");
				return "teacherregister";
			} else if (user.getIntroduction() == null
					|| user.getMajor() == null || user.getPassword() == null
					|| user.getName() == null || user.getAmount() == null
					|| user.getJobtitle() == null) {
				model.addAttribute("msg", "数据不能为空");
				return "teacherregister";
			} else {
				if (userService.register(user, validateCode)) {
					model.addAttribute("user", user);
					// return "register";
					return "redirect:/login";
				} else {
					model.addAttribute("msg", "学号或密码错误");
					return "teacherregister";
				}
			}
		}
	}

	@RequestMapping("/myinfo")
	public String getMyinfo(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return null;
	}

	@RequestMapping(value = "/imporStudentXls", method = RequestMethod.GET)
	public String importStudentXlsView(HttpSession session, Model model) {
		return "user/file-add";
	}

	@RequestMapping(value = "/imporStudentXls", method = RequestMethod.POST)
	public String importStudentXls(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, Model model) {
		 User user = (User) session.getAttribute("user");
		 String path =
		 request.getSession().getServletContext().getRealPath("") +
		 "/upload/";//
		 String fileName = file.getOriginalFilename();
		 fileName = System.currentTimeMillis() +
		 fileName.substring(fileName.indexOf("."));
		 File targetFile = new File(path, fileName);
		 if(!targetFile.exists()){
		 targetFile.mkdirs();
		 }
		 try {
			 file.transferTo(targetFile);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 String filePathName = path + fileName;
		userService.importStudentXls(new File(filePathName));
		model.addAttribute("msg", "成功");
		return "notice-msg";
		// }
	}

	@RequestMapping(value = "/imporTeachertXls", method = RequestMethod.GET)
	public String importTeacherXlsView(HttpSession session, Model model) {
		return "user/file-add";
	}

	@RequestMapping(value = "/imporTeachertXls", method = RequestMethod.POST)
	public String importTeacherXls(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, Model model) {
		 User user = (User) session.getAttribute("user");
		 String path =
		 request.getSession().getServletContext().getRealPath("") +
		 "/upload/";//
		 String fileName = file.getOriginalFilename();
		 fileName = System.currentTimeMillis() +
		 fileName.substring(fileName.indexOf("."));
		 File targetFile = new File(path, fileName);
		 if(!targetFile.exists()){
		 targetFile.mkdirs();
		 }
		 try {
		 file.transferTo(targetFile);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 String filePathName = path + fileName;
		userService.importTeacherXls(new File(filePathName));
		model.addAttribute("msg", "成功");
		return "notice-msg";
		// }
	}

	@RequestMapping("/modifyinfo")
	public String importTeacherXls(User userTmp, HttpServletRequest request,
			HttpSession session) {
		User user = (User) session.getAttribute("user");
		String method = request.getMethod();
		if ("GET".equals(method)) {
			if (user.isStudent()) {
				return "studentmodify";
			} else {
				return "teachermodify";
			}
		} else {
			user.copyUser(userTmp);
			userService.updateUserById(user);
			return null;
		}
	}
}
