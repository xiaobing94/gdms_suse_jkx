package com.gdms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.read.biff.BiffException;

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

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(String work_id, String password, Model model,
			HttpSession session) {
		// String method = request.getMethod();
		// if ("GET".equals(method)) {
		// return "login";
		// } else {
		if (userService.login(session, work_id, password)) {
			// System.out.println("user��");
			return "redirect:/";
		} else {
			// System.out.println("login false");
			model.addAttribute("msg", "��������ȷ����Ϣ");
			return "login";
		}
		// }
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/login";
	}

	@RequestMapping(value = "/student_register", method = RequestMethod.GET)
	public String studentRegister() {
		return "register";
	}

	@RequestMapping(value = "/student_register", method = RequestMethod.POST)
	public String studentRegister(User user, String validateCode,
			HttpServletRequest request, Model model) {
		// String method = request.getMethod();
		// if ("GET".equals(method)) {
		// return "register";
		// } else {
		if (user == null) {
			model.addAttribute("msg", "userΪ��");
			return "register";
		} else if (user.getIntroduction() == null || user.getMajor() == null
				|| user.getPassword() == null || user.getName() == null
				|| user.getGrade() == null) {
			model.addAttribute("msg", "���ݲ���Ϊ��");
			// System.out.println("����������");
			return "register";
		} else {
			if (userService.register(user, validateCode)) {
				model.addAttribute("user", user);
				// return "register";
				return "redirect:/login";
			} else {
				model.addAttribute("msg", "ѧ�Ż��������");
				return "register";
			}
		}
		// }
	}

	@RequestMapping(value = "/teacher_register", method = RequestMethod.GET)
	public String teacherRegister() {
		return "teacherregister";
	}

	@RequestMapping(value = "/teacher_register", method = RequestMethod.POST)
	public String teacherRegister(User user, String validateCode,
			HttpServletRequest request, Model model) {
		// String method = request.getMethod();
		// if ("GET".equals(method)) {
		// return "teacherregister";
		// } else {
		if (user == null) {
			model.addAttribute("msg", "userΪ��");
			return "teacherregister";
		} else if (user.getIntroduction() == null || user.getMajor() == null
				|| user.getPassword() == null || user.getName() == null
				|| user.getAmount() == null || user.getJobtitle() == null) {
			model.addAttribute("msg", "���ݲ���Ϊ��");
			return "teacherregister";
		} else {
			if (userService.register(user, validateCode)) {
				model.addAttribute("user", user);
				// return "register";
				return "redirect:/login";
			} else {
				model.addAttribute("msg", "ѧ�Ż��������");
				return "teacherregister";
			}
		}
		// }
	}

	@RequestMapping("/myinfo")
	public String getMyinfo(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		if (user.isStudent()) {
			return "user/student-detail";
		} else {
			return "user/teacher-detail";
		}
	}

	@RequestMapping(value = "/imporStudentXls", method = RequestMethod.GET)
	public String importStudentXlsView(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user.haveDirectorPermission() || user.haveLeaderPermission()) {
			return "user/file-add";
		} else {
			model.addAttribute("msg", "�㲻�ǵ�ʦ����Ժ��");
			return "notice-msg";
		}
	}

	@RequestMapping(value = "/imporStudentXls", method = RequestMethod.POST)
	public String importStudentXls(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/";//
		String fileName = file.getOriginalFilename();
		fileName = System.currentTimeMillis()
				+ fileName.substring(fileName.indexOf("."));
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filePathName = path + fileName;
		try {
			boolean isSucc = userService
					.importStudentXls(new File(filePathName));
			if (isSucc) {
				model.addAttribute("msg", "�ɹ�");
			}else{
				model.addAttribute("msg", "�ļ���ʽ�������ݴ���");
			}
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "�ļ���ʽ�������ݴ���");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg", "�ļ���ʽ�������ݴ���");
		}
		return "notice-msg";
		// }
	}

	@RequestMapping(value = "/imporTeachertXls", method = RequestMethod.GET)
	public String importTeacherXlsView(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user.haveDirectorPermission() || user.haveLeaderPermission()) {
			return "user/file-add";
		} else {
			model.addAttribute("msg", "�㲻�ǵ�ʦ����Ժ��");
			return "notice-msg";
		}
	}

	@RequestMapping(value = "/imporTeachertXls", method = RequestMethod.POST)
	public String importTeacherXls(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/upload/";//
		String fileName = file.getOriginalFilename();
		fileName = System.currentTimeMillis()
				+ fileName.substring(fileName.indexOf("."));
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filePathName = path + fileName;
		boolean isSucc = userService.importTeacherXls(new File(filePathName));
		if(isSucc){
			model.addAttribute("msg", "�ɹ�");
		}
		else{
			model.addAttribute("msg", "�ļ���ʽ�������ݴ���");
		}
		return "notice-msg";
		// }
	}

	@RequestMapping(value = "/modifyinfo", method = RequestMethod.GET)
	public String modifyInfo(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (user.isStudent()) {
			return "user/student-detail-modify";
		} else {
			return "user/teacher-detail-modify";
		}
	}

	@RequestMapping(value = "/modifyinfo", method = RequestMethod.POST)
	public String modifyInfo(User userTmp, HttpServletRequest request,
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		// String method = request.getMethod();
		// if ("GET".equals(method)) {
		// if (user.isStudent()) {
		// return "user/studentmodify";
		// } else {
		// return "user/teachermodify";
		// }
		// } else {
		user.copyUser(userTmp);
		userService.updateUserById(user);
		model.addAttribute("msg", "�޸ĳɹ�");
		return "notice-msg";
		// }
	}

	@RequestMapping(value = "/modifypassword", method = RequestMethod.GET)
	public String modifyPassword() {
		return "user/modify-password";
	}

	@RequestMapping(value = "/modifypassword", method = RequestMethod.POST)
	public String modifyPassword(String oldPassword, String password,
			String repeatPassword, HttpServletRequest request,
			HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		if (!password.equals(repeatPassword)) {
			model.addAttribute("msg", "�ظ����벻ͬ");
		} else if (userService.verifyUserPassword(user, oldPassword)) {
			userService.modifyPassword(user, oldPassword, password);
			model.addAttribute("msg", "�޸ĳɹ�");
		} else {
			model.addAttribute("msg", "�޸�ʧ�ܣ���ǰ���������");
		}
		return "notice-msg";
	}
}
