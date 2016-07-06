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
			model.addAttribute("msg", "创建成功");
		}
		else if(user.isStudent()){
			User teacher = tutorStuService.getTeacherByStudent(user.getId());
			if(teacher==null){
				model.addAttribute("msg", "还没有确定导师");
			}else{
				issue.setUserId(teacher.getId());
				issueService.createIssue(issue);
				model.addAttribute("msg", "创建成功");
			}
			
		}else{
			model.addAttribute("msg", "你没有创建权限");
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
			return publishmsg("选择课题成功", response);
		}else{
			return publishmsg("你没有选择课题的权限", response);
		}
	}
	@RequestMapping("/getMyTutor")
	public String getMyTutor(HttpSession session, Model model){
		/**
		 * 获取学生的导师
		 */
		User student = (User) session.getAttribute("user");
		User teacher = tutorStuService.getTeacherByStudent(student.getId());
		if(teacher==null){
			model.addAttribute("msg", "你当前阶段还没有确定唯一的导师，请查看你的进度状态");
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
		model.addAttribute("msg", "删除成功");
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
//			model.addAttribute("msg", "你没有权限");
//			return "notice-msg";
//		}
//		issueService.getStudentIssue(user);
//		//Issue issue = issueService.getContent(issue_id);
//		//model.addAttribute("issue", issue);
//		return "teacher/topic-info";
//	}
}
