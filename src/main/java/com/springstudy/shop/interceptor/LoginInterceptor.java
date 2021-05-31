package com.springstudy.shop.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 실무에서는 Security씀
public class LoginInterceptor extends HandlerInterceptorAdapter{
		// 자바 상수표기 변수이름 대문자 (명명룰)
	private static final String LOGIN = "login";
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
				
		HttpSession session = request.getSession();
			// model, view쪽에서 쓰임 (=model객체를 못쓴다는뜻)
		ModelMap modelMap = modelAndView.getModelMap();
		// String contextPath = request.getContextPath();
		
		Object memInfo = modelMap.get("memInfo");
			
			// memInfo가 null이 아니라면 로그인 성공
		if(memInfo != null) {
			log.info("new Login success");
			session.setAttribute(LOGIN, memInfo);
			
			Object dest = session.getAttribute("dest");
			// response.sendRedirect("/");
			
			if(dest == null) {
				dest = "/board/list";
			}
			log.info("LoginInterceptor Dest: " + dest);
			
			modelAndView.setViewName("redirect:" + (String)dest);
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) {
				// 로그인 이전데이터를 클리어처리.
			log.info("clear Login data before..");
			session.removeAttribute(LOGIN);
		}
		return true;
	}

}
