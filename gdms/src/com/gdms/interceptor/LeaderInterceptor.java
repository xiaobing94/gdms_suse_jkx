package com.gdms.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gdms.controller.ErrorController;
import com.gdms.pojo.User;

public class LeaderInterceptor implements HandlerInterceptor {
	private List<String> excludedUrls;// 不拦截的url

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

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		String requestUri = request.getRequestURI();
		for (String url : excludedUrls) {
			if (requestUri.contains(url)) {
				return true;
			}
		}
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println("导师权限:"+user.haveLeaderPermission());
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else if(user.haveLeaderPermission()){
			return true;
		}else{
			//response.sendRedirect(request.getContextPath() + "/error?error=你没有导师权限");
			String url=request.getContextPath() + "/error?code="+ErrorController.LEADER;
			System.out.println(url);
			response.sendRedirect(url);
			return false;
		}
	}

}
