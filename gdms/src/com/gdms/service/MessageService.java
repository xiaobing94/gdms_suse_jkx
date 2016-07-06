package com.gdms.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gdms.dao.MessageMapper;
import com.gdms.pojo.Message;
import com.gdms.pojo.User;


@Service("messageService")
public class MessageService {
	@Resource
	private MessageMapper messageDAO;

	public MessageMapper getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageMapper messageDAO) {
		this.messageDAO = messageDAO;
	}
	public void sendMessage(Message msg){
		/*
		 * 发送消息
		 */
		messageDAO.insertSelective(msg);
	}
	public List<Message> getMessageListByUser(User user){
		/*
		 * 获取用户的消息列表
		 */
		
		return messageDAO.selectByUserId(user.getId());
	}
	public List<Message> getUnreadMessageListByUser(User user){
		/*
		 * 获取用户的未读消息列表
		 */
		return messageDAO.selectUnreadByUserId(user.getId());
	}
	public List<Message> getReadMessageListByUser(User user){
		/*
		 * 获取用户的已读消息列表
		 */
		return messageDAO.selectReadByUserId(user.getId());
	}
	public List<Message> getSendMessageListByUser(User user){
		/*
		 * 获取用户的已读消息列表
		 */
		return messageDAO.selectSendByUserId(user.getId());
	}
	public Message getMessageContent(int msgId)
	{
		/**
		 * 获取内容
		 */
		Message msg = messageDAO.selectByPrimaryKey(msgId);
		msg.setStatus(Message.READ);
		messageDAO.updateByPrimaryKeySelective(msg);
		return msg;
	}
	public List<Message> getMessageListById(int msgId)
	{
		/**
		 * 获取内容
		 */
		Message message = messageDAO.selectByPrimaryKey(msgId);
		List<Message> msgList = messageDAO.selectByUsers(message.getRecvId(), message.getFromId());
		Collections.sort(msgList, new Comparator<Message>() {

			@Override
			public int compare(Message msg1, Message msg2) {
				// TODO Auto-generated method stub
				return msg1.getDateline().compareTo(msg2.getDateline());
			}
        });
		for(Message msg:msgList){
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			Date date = msg.getDateline();
			String dateStr = sdf.format(date);
			System.out.println("date:"+dateStr);
		}
		return msgList;
	}
}
