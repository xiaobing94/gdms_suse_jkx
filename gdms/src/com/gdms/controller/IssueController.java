package com.gdms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdms.pojo.Issue;
import com.gdms.pojo.User;
import com.gdms.service.IssueService;
import com.gdms.service.TutorStuService;
import com.gdms.service.UserService;

@Controller
@RequestMapping("/issue")
public class IssueController extends BaseController{
	@Resource
	private IssueService issueService;
	@Resource
	private UserService userService;
	@Resource
	private TutorStuService tutorStuService;
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
	public IssueService getIssueService() {
		return issueService;
	}
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createView(HttpSession session, Model model){
		return "teacher/add-topic";
	}
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(HttpSession session, Issue issue, Model model){
		User user = (User) session.getAttribute("user");
		if(user.haveTeacherPermission()){
			issue.setUserId(user.getId());
			issueService.createIssue(issue);
			model.addAttribute("msg", "�����ɹ�");
		}
		else if(user.isStudent()){
			User teacher = tutorStuService.getTeacherByStudent(user.getId());
			if(teacher==null){
				model.addAttribute("msg", "��û��ȷ����ʦ");
			}else{
				issue.setUserId(teacher.getId());
				issueService.createIssue(issue);
				model.addAttribute("msg", "�����ɹ�");
			}
			
		}else{
			model.addAttribute("msg", "��û�д���Ȩ��");
		}
		return "notice-msg";
		
	}
//	@RequestMapping("/studentCreate")
//	public String studentCreate(Issue issue){
//		issueService.createIssue(issue);
//		return null;
//	}
	@RequestMapping("/getlistbyteacher")
	public String getListByTeacher(int teacher_id, Model model){
		List<Issue> issueList = issueService.getIssueListByTeacher(teacher_id);
		model.addAttribute("issueList", issueList);
		return "teacher/student-degree-list";
	}
	@RequestMapping("/choise")
	public String choiseIssue(HttpSession session, int issue_id, HttpServletResponse response){
		User user = (User) session.getAttribute("user");
		if(user.isStudent()){
			user.setIssueId(issue_id);
			userService.updateUserById(user);
			return publishmsg("ѡ�����ɹ�", response);
		}else{
			return publishmsg("��û��ѡ������Ȩ��", response);
		}
	}
	@RequestMapping("/getMyTutor")
	public String getMyTutor(HttpSession session, Model model){
		/**
		 * ��ȡѧ���ĵ�ʦ
		 */
		User student = (User) session.getAttribute("user");
		User teacher = tutorStuService.getTeacherByStudent(student.getId());
		if(teacher==null){
			model.addAttribute("msg", "�㵱ǰ�׶λ�û��ȷ��Ψһ�ĵ�ʦ����鿴��Ľ���״̬");
			return "notice-msg";
		}
		List<Issue> issueList = issueService.getIssueListByTeacher(teacher.getId());
		//System.out.println("size:"+issueList.size());
		model.addAttribute("issueList", issueList);
		model.addAttribute("teacher", teacher);
		return "teacher/teacher-info";
	}
	@RequestMapping("/issueList")
	public String getIssueList(HttpSession session, Model model){
		User user = (User) session.getAttribute("user");
		List<Issue> issueList = issueService.getIssueListByTeacher(user.getId());
		model.addAttribute("issueList", issueList);
		return "issue/issue-list-for-del";
	}
	@RequestMapping("/del")
	public String delIssueList(int[] issue_id, HttpSession session, Model model){
		issueService.deleteIssueByArr(issue_id);
		model.addAttribute("msg", "ɾ���ɹ�");
		return "notice-msg";
	}
	@RequestMapping("/issue-detail")
	public String issueDetail(int issue_id, HttpSession session, Model model){
		Issue issue = issueService.getContent(issue_id);
		model.addAttribute("issue", issue);
		return "teacher/topic-info";
	}
//	@RequestMapping("/getMyIssue")
//	public String getMyIssue(HttpSession session, Model model){
//		User user = (User) session.getAttribute("user");
//		if(!user.isStudent()){
//			model.addAttribute("msg", "��û��Ȩ��");
//			return "notice-msg";
//		}
//		issueService.getStudentIssue(user);
//		//Issue issue = issueService.getContent(issue_id);
//		//model.addAttribute("issue", issue);
//		return "teacher/topic-info";
//	}
}
