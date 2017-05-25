package com.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.gxb.manager.control.SysLoginControl;
import com.gxb.model.SysLoginInfo;



public class BaseInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String url = request.getRequestURI();
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			Object obj = method.getBean();
			if (obj instanceof SysLoginControl || url.indexOf("/web")!=-1) {
				return super.preHandle(request, response, handler);
			} else {
				if (url.indexOf("/web/") == -1) {
					SysLoginInfo info = (SysLoginInfo) request.getSession()
							.getAttribute("usr");
					if (null == info&&request.getParameter("userId")==null) {
						if (url.indexOf("manager") > 0
								&& url.indexOf("tologin") == -1) {
							response.sendRedirect("tologin");
						} else {
							response.sendRedirect("manager/login");
						}
						return false;
					}
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
}
