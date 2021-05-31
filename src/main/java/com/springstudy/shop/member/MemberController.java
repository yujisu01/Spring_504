package com.springstudy.shop.member;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springstudy.shop.member.domain.MemberDTO;
import com.springstudy.shop.member.service.IMemberService;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private IMemberService service;
	
	@GetMapping("/login")
		// login.jsp생성해야함
	public void loginGET(@ModelAttribute("mDto") MemberDTO mDto) {
		
		
	}
	@PostMapping("/loginPost")
		// 반환할떄 사용할객체(model)
	public String loginPOST(MemberDTO mDto, HttpSession session, Model model) throws Exception {
		String returnURL = "";
		MemberDTO memInfo = service.login(mDto);
		log.info("memInfo ===> " + memInfo);
		
		if(memInfo == null) {
			return "redirect:/member/login";
		}
		model.addAttribute("memInfo",memInfo);
		
		return returnURL;
	}
	
	@GetMapping("/logout")
		// 세션날리고 메인페이지로 보내면됨
		// 세션객체롤 넘겨받기위해서 주입
	public String logout(HttpSession session) {
		log.info("logout.............................");
		
		session.getAttribute("login");
		Object obj = session.getAttribute("login");
		
		if(obj != null) {
			session.removeAttribute("login");
			session.invalidate();
		}
			// 개인프로젝트에는 main으로 꽂아야함.
		return "redirect:/board/list";
	}

}
