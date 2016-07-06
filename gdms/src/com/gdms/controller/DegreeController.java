package com.gdms.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdms.pojo.Comment;
import com.gdms.pojo.Degree;
import com.gdms.pojo.Issue;
import com.gdms.pojo.User;
import com.gdms.service.DegreeService;
import com.gdms.service.IssueService;
import com.gdms.service.TutorStuService;
import com.gdms.service.UserService;

@Controller
@RequestMapping("degree")
public class DegreeController {
	@Resource
	private DegreeService degreeService;
	@Resource
	private UserService userService;
	@Resource
	private TutorStuService tutorStuService;
	@Resource
	private IssueService issueService;

	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
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

	public DegreeService getDegreeService() {
		return degreeService;
	}

	public void setDegreeService(DegreeService degreeService) {
		this.degreeService = degreeService;
	}

	@RequestMapping("/getmydegreelist")
	public String getMyDegreeList(HttpSession session, Model model) {
		/**
		 * 获取自己的进度列表
		 */
		User user = (User) session.getAttribute("user");
		if (user.isStudent()) {
			getDegreeListByStudentId(user.getId(), model);
		} else {
			model.addAttribute("msg", "没有权限");
		}
		Issue issue = issueService.getStudentIssue(user);
		model.addAttribute("issue", issue);
		return "teacher/student-degree-list";
	}

	public void getDegreeListByStudentId(int studentId, Model model) {
		List<Degree> degreeList = degreeService
				.getDegreeListByStudentId(studentId);
		model.addAttribute("degreeList", degreeList);
	}

	@RequestMapping("/getDegreelist")
	public String getStudentDegreeList(int studentId, Model model) {
		/**
		 * 获取学生进度列表
		 */
		getDegreeListByStudentId(studentId, model);
		return "teacher/student-degree-list-for-teacher";
	}

	// @RequestMapping("/getmystudentlist")
	// public String getStudentDegreeList(){
	// /**
	// * 获取老师自己的学生列表
	// */
	// return null;
	// }
	// @RequestMapping("/getlistbystudent")
	// public String getDegreeListByStudent(int student_id){
	// /**
	// * 获取某个学生的进度列表
	// */
	// return null;
	// }
	@RequestMapping(value="/upload_degree", method=RequestMethod.GET)
	public String createDegreeView(){
		return "teacher/degree-add";
	}
	@RequestMapping(value="/upload_degree", method=RequestMethod.POST)
	public String createDegree(@RequestParam(value = "file", required = false) MultipartFile file, Degree degree,HttpSession session, HttpServletRequest request, Model model) {
		/**
		 * 创建进度
		 */
		User user = (User) session.getAttribute("user");
		degree.setStudentId(user.getId());
		String path = request.getSession().getServletContext().getRealPath("") + "/upload/";//
		String fileName = file.getOriginalFilename();
		fileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("."));
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
        System.out.println("文件上传至\n" + filePathName + "\n成功！");
        degree.setPath("upload/"+fileName);
        degreeService.create(degree);
        model.addAttribute("msg", "上传成功");
		return "notice-msg";
	}
	@RequestMapping(value="/comment", method=RequestMethod.GET)
	public String commentDegreeView(int degreeId, Model model){
		model.addAttribute("degreeId", degreeId);
		return "teacher/comment-add";
	}
	@RequestMapping(value="/comment", method=RequestMethod.POST)
	public String commentDegree(Comment comment, HttpSession session,
			Model model) {
		/**
		 * 老师评论进度
		 */
		User user = (User) session.getAttribute("user");
		degreeService.commentDegree(comment, user);
		model.addAttribute("msg", "评论成功");
		return "notice-msg";
	}
	@RequestMapping("/getStudentList")
	public String getStudentListByUser(HttpSession session, Model model){
		/**
		 * 获取老师自己的学生列表
		 */
		User teacher = (User) session.getAttribute("user");
		List<User> studentList = userService.getStudentByTeacher(teacher);
		model.addAttribute("studentList", studentList);
		return "teacher/student-list";
	}
	@RequestMapping("getCommentByDegreeId")
	public String getCommentByDegreeId(int degreeId, HttpSession session, Model model){
		List<Comment> commentList = degreeService.getCommentListByDegreeId(degreeId);
		model.addAttribute("commentList", commentList);
		return "teacher/comment-list";
	}
}
