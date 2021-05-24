package com.springstudy.shop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CommonExceptionAdvice {
	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		// 문자열 뿌리고 괄호안으로 ex객체가 괄호안으로 전달된다. 
		// = HomeController 31Line표현법
		logger.error("Exception......................{}", ex);	// ("Exception..." + ex) 랑 똑같음
		
		// 화면에서 꺼내올떄는 exception으로 꺼낸다. (식별자 exception으로 ex를 명시함?)
		model.addAttribute("exception", ex);
		logger.error("Model......................{}", model);
		return "error_page";
	}
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	// 404에러를 핸들링하겠다
	public String handle404(NoHandlerFoundException ex) {
		logger.info("errorpage............................");
		return "custom404";
		
	}

}
