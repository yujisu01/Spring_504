package com.springstudy.shop;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	// 매소드 호출을 위해 RequestMapping 붙인거.
	// Get방식으로 왔을때 이메소드가 호출되게.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// Locale이라는 클래스가 있다. 이걸 주입해서 여기에 현재위치에 관련된 정보 호출.
	// 화면에 전달할때 Model사용.
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		// 현재날짜를 취득해서 
		Date date = new Date();
		// DateFormat을 해줌. (SimpleFormat을 평소에 더많이사용한다)
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		// 담아서
		String formattedDate = dateFormat.format(date);
		
		// serverTime이라는 이름(식별자)으로 보내줌
		model.addAttribute("serverTime", formattedDate );
		
		// home이라는 jsp로 이동 (이 정보들을 다 싣고)
		return "home";
	}
	
}
