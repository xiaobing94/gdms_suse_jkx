package com.gdms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdms.pojo.User;
import com.gdms.service.StuTutorService;
import com.gdms.service.TutorStuService;
import com.gdms.service.UserService;

@Controller
@RequestMapping("/choise")
public class ChoiseController extends BaseController {
	@Resource
	private UserService userService;
	@Resource
	private StuTutorService stuTutorService;
	@Resource
	private TutorStuService tutorStuService;
	
	public StuTutorService getStuTutorService() {
		return stuTutorService;
	}
	public void setStuTutorService(StuTutorService stuTutorService) {
		this.stuTutorService = stuTutorService;
	}
	public TutorStuService getTutorStuService() {
		return tutorStuService;
	}
	public void setTutorStuService(TutorStuService tutorStuService) {
		this.tutorStuService = tutorStuService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@RequestMapping("/teacher_list")
	public String getTeacherList(HttpSession session, Model model){
		/*
		 * ��ñ�רҵ���е���ʦ�б�
		 */
		User user = (User) session.getAttribute("user");
		List<User> teacherList = userService.getTeacherByMajor(user.getMajor());
		for(int i=0; i<teacherList.size();i++){
			User teacher = teacherList.get(i);
			teacher.setTotal(stuTutorService.getAmountByTeacherId(teacher.getId()));
		}
		model.addAttribute("teacherList", teacherList);
		return "gd/choose-teacher";
	}
	@RequestMapping("/choise-teacher")
	public String  stuTutor(int teacher_id, HttpSession session, HttpServletResponse response){
		User user =  (User) session.getAttribute("user");
//		Map<String, Object> map =new HashMap<String, Object>();
//		ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView(),map);  
		if(user==null){
//			map.put("msg", "���¼");
//			return modelAndView;
			return publishmsg("���¼", response);
		}
		if(stuTutorService.create(teacher_id, user)){
//			map.put("msg", "�ɹ�");
			return publishmsg("�ɹ�", response);
		}
		else{
//			map.put("msg","ʧ��");
			return publishmsg("ʧ��:��ʦδ���ÿ�ѡ�������򳬹�����", response);
		}
		
	}
	@RequestMapping("/student_list")
	public String getStudentList(HttpSession session, Model model){
		/*
		 * ���ѡ���Լ���ѧ���б�
		 */
		User user =  (User) session.getAttribute("user");
		if(!user.haveTeacherPermission()){
			model.addAttribute("msg","��û����ʦȨ��");
			return "gd/as-student-list";
		}
		else{
			List<User> studentList = userService.getStudentByTeacher(user);
			model.addAttribute("studentList", studentList);
			return "gd/as-student-list";
		}
	}
	@RequestMapping("/choise-student")
	public  String tutorStu(int student_id, HttpSession session, Model model, HttpServletResponse response){
		User user =  (User) session.getAttribute("user");
		if(!user.haveTeacherPermission()){
			return publishmsg("��û�����Ȩ��", response);
		}
		else{
			if(tutorStuService.create(student_id, user.getId())){
				return publishmsg("�ɹ�", response);
			}
			else{
				return publishmsg("ʧ�ܣ�����ԭ�򣺵������ޣ�����û����������", response);
			}
		}
	}
	@RequestMapping("/studentsmajor")
	public String studentsMajor(int teacherId, HttpSession session, Model model)
	{
		/**
		 * ����ѧ������ͼ������ѧ���б�����ʦ�б�
		 */
		User user =  (User) session.getAttribute("user");
		List<User> studentList = tutorStuService.getLastStudentByMajor(user.getMajor());
		//List<User> teacherList = userService.getTeacherByMajor(user.getMajor());
		model.addAttribute("studentList", studentList);
		model.addAttribute("teacherId", teacherId);
		//model.addAttribute("teacherList", teacherList);
		return "gd/student-list";
	}
	@RequestMapping("/teachermajor")
	public String showteacherList(HttpSession session, Model model)
	{
		/**
		 * ����ѧ������ͼ������ѧ���б�����ʦ�б�
		 */
		User user =  (User) session.getAttribute("user");
		//List<User> studentList = tutorStuService.getLastStudentByMajor(user.getMajor());
		List<User> teacherList = userService.getTeacherByMajor(user.getMajor());
		//model.addAttribute("studentList", studentList);
		model.addAttribute("teacherList", teacherList);
		return "gd/as-teacher-list";
	}
	@RequestMapping("/distribute")
	public String distributeStudent(int[] studentId, int teacherId, HttpServletResponse response)
	{
		/**
		 * ����ѧ��
		 */
		tutorStuService.distributeStudent(studentId, teacherId);
		return publishmsg("�ɹ�", response);
	}
	@RequestMapping("/xlsexport")
	public String xlsExport(String test, HttpSession session, HttpServletRequest request, Model model){
		String path = request.getSession().getServletContext().getRealPath("") + "/upload/";
		System.out.println("path:"+path);
		User user =  (User) session.getAttribute("user");
		if(test!=null&&test.equals("test")){
			tutorStuService.exportAllXls(path+"collegeresult.xls");
			return "redirect:"+"/upload/collegeresult.xls";
		}
		if(user.haveDirectorPermission()){
			tutorStuService.exportMajorXls(user.getMajor(), path+"result.xls");
			return "redirect:"+"/upload/result.xls";
		}else if(user.haveLeaderPermission()){
			tutorStuService.exportAllXls(path+"collegeresult.xls");
			return "redirect:"+"/upload/collegeresult.xls";
		}
		else{
			model.addAttribute("msg", "û��Ȩ��");
			return "notice-msg";
		}
		
	}
}