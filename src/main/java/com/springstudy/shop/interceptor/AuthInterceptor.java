package com.springstudy.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

	// 인증절차를 별도로 사용하겠다
public class AuthInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	
	private void saveDest(HttpServletRequest request) {
		String uri = request.getRequestURI();
		// 원래사용자가 원하는 페이지의 정보취득
		String contextPath = request.getContextPath();
		
		uri = uri.substring(contextPath.length());
		log.info("uri ==> " + uri);
		
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query ="";
		}else {
			query = "?" + query;
		}
			// get방식 처리..
		if(request.getMethod().equals("GET")) {
			log.info("dest : " + (uri + query));
			
			request.getSession().setAttribute("dest", uri + query);
		}
		
	}
		// get방식처리전에 선행작업
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			HttpSession session = request.getSession();
			String contextPath = request.getContextPath();
			
			if(session.getAttribute("login") == null) {
					// 아직 접속안함
				log.info("current user is not logined");
				
				saveDest(request);
				
				response.sendRedirect(contextPath + "/member/login");
				
				return false;
			}
			
			return true;
		}
}
