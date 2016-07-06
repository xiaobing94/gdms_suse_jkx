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
		 * ������Ϣ
		 */
		messageDAO.insertSelective(msg);
	}
	public List<Message> getMessageListByUser(User user){
		/*
		 * ��ȡ�û�����Ϣ�б�
		 */
		
		return messageDAO.selectByUserId(user.getId());
	}
	public List<Message> getUnreadMessageListByUser(User user){
		/*
		 * ��ȡ�û���δ����Ϣ�б�
		 */
		return messageDAO.selectUnreadByUserId(user.getId());
	}
	public List<Message> getReadMessageListByUser(User user){
		/*
		 * ��ȡ�û����Ѷ���Ϣ�б�
		 */
		return messageDAO.selectReadByUserId(user.getId());
	}
	public Message getMessageContent(int msgId)
	{
		/**
		 * ��ȡ����
		 */
		Message msg = messageDAO.selectByPrimaryKey(msgId);
		msg.setStatus(Message.READ);
		messageDAO.updateByPrimaryKeySelective(msg);
		return msg;
	}
	public List<Message> getMessageListById(int msgId)
	{
		/**
		 * ��ȡ����
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
