package com.gdms.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * ��֤�Ƿ�Ϊapp����
 * 
 * @author Administrator
 * 
 */
@Controller
public class BaseController {
	public boolean verifyClient(HttpServletRequest request) {
		String is_app = (String) request.getAttribute("is_app");
		if (is_app == null || !is_app.equals("1")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * �����˿ͻ�����֤
	 * 
	 * @param request
	 * @param msg
	 *            ��Ҫ���ص���Ϣ
	 * @param returnView
	 *            ���ص���ͼ
	 * @return
	 */
	public String publishmsg(String msg, HttpServletResponse response) {
		outJson("{\"msg\":\"" + msg + "\"}", response);
		return null;
	}

	/**
	 * ����json�ַ���
	 * 
	 * @param request
	 * @param json
	 *            json��ʽ���ַ���
	 * @param returnView
	 *            ���ص���ͼ
	 * @return
	 */
	public String publishJson(String json, HttpServletResponse response) {
		//response.setContentType("");
		outJson(json, response);
		return null;
	}

	public String mapToJson(Map map) {
		String resultStr = "{";
		for (Object key : map.keySet()) {
			String str = "\"" + key + "\":\"" + map.get(key) + "\",";
			resultStr = resultStr + str;
		}
		if (resultStr.charAt(resultStr.length() - 1) == ',') {
			resultStr.substring(0, resultStr.length() - 1);
		}
		return resultStr + "}";
	}

	/**
	 * ͨ��key����List����һ�� "key" : [ "val", "val"]
	 * 
	 * @param key
	 * @param list
	 * @return "key" : [ "val", "val"]
	 */
	public String listToJson(String key, List list) {
		String json = "\"" + key + "\": [";
		for (int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			if (i != list.size() - 1) {
				json = json + object + ", ";
			} else {
				json = json + object;
			}
		}
		return json;
	}

	public String objectToJson(String key, Object object) {
		return "{\"" + key + "\": \"" + object + "\"}";
	}

	public void outJson(String json, HttpServletResponse response) {
		// HttpServletRequest request = ((ServletRequestAttributes)
		// RequestContextHolder.getRequestAttributes()).getRequest();
		// ServletWebRequest servletWebRequest=new ServletWebRequest(request);
		// HttpServletResponse response=servletWebRequest.getResponse();
		response.setContentType("application/json; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.print(json);
		out.flush();
		out.close();
	}
}
