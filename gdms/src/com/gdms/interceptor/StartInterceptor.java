package com.gdms.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gdms.controller.ErrorController;
import com.gdms.tools.PropertiesLog4j;

public class StartInterceptor implements HandlerInterceptor {
	private List<String> excludedUrls;//不拦截的url
	public List<String> getExcludedUrls() {
		return excludedUrls;
	}

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("执行到拦截器:size:"+excludedUrls.size());
		String requestUri = request.getRequestURI();
        for (String url : excludedUrls) {
            if (requestUri.contains(url)) {
                return true;
            }
        }
        PropertiesLog4j p4j = new PropertiesLog4j();
        Date date = p4j.getStartDate();
        Date nowDate = new Date();
        boolean flag = date.before(nowDate); 
        if(flag){
        	return true;
        }else{
        	response.sendRedirect(request.getContextPath() + "/error?code="+ErrorController.START);
        	return false;
        }
		
	}

}
