package com.gdms.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdms.pojo.Message;
import com.gdms.pojo.User;
import com.gdms.service.MessageService;
import com.gdms.service.UserService;

@Controller
@RequestMapping("/message")
public class MessageController {
	@Resource
	private MessageService messageService;
	@Resource
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public String sendMessage(int recvId, Model model) {
		model.addAttribute("recvId", recvId);
		return "message/message-add";
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public String sendMessage(Message msg, HttpSession session, Model model) {
		// String method = request.getMethod();
		// if ("GET".equals(method)) {
		// return "message/message-add";
		// } else {
		Date date = new Date();
		if (msg.getNote() == null || msg.getRecvId() == null) {
			model.addAttribute("msg", "数据不能空");
			return "notice-msg";
		} else {
			User user = (User) session.getAttribute("user");
			msg.setFromId(user.getId());
			msg.setStatus(0);
			msg.setDateline(date);
			messageService.sendMessage(msg);
			model.addAttribute("msg", "发送成功");
			return "notice-msg";
		}

	}

	@RequestMapping("/list")
	public String messageList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Message> readmessageList = messageService.getReadMessageListByUser(user);
		List<Message> unreadmessageList = messageService.getUnreadMessageListByUser(user);
		model.addAttribute("unreadmessageList", unreadmessageList);
		model.addAttribute("readmessageList", readmessageList);
		return "message/msg-list";
	}

	@RequestMapping("/nureadlist")
	public String messageUnreadList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Message> messageList = messageService
				.getUnreadMessageListByUser(user);
		model.addAttribute("unreadmessageList", messageList);
		return null;
	}

	@RequestMapping("/detail")
	public String messageContent(int msg_id, HttpSession session, Model model) {
//		Message message = messageService.getMessageContent(msg_id);
//		model.addAttribute("message", message);
		User user = (User) session.getAttribute("user");
		List<Message> msgList = messageService.getMessageListById(msg_id);
		for(Message msg: msgList){
			msg.setRecver(userService.getUser(msg.getRecvId()));
			msg.setFromer(userService.getUser(msg.getFromId()));
		}
		Message msg = messageService.getMessageContent(msg_id);
		model.addAttribute("user", user);
		model.addAttribute("msgList", msgList);
		model.addAttribute("message", msg);
		return "message/msg-detail";
	}
}
