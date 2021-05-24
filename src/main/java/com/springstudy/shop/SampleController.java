package com.springstudy.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springstudy.shop.domain.SampleDTO;
import com.springstudy.shop.domain.SampleDTOList;
import com.springstudy.shop.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller		// // 컨트롤러라고 스프링에게 알려주는 Annotation
@RequestMapping("/sample/*")	// sample로 이렇게 들어온다면 basic이 실행된다.
@Log4j

public class SampleController {
	// lombok없을때 logger사용법
	private static final Logger logger = LoggerFactory.getLogger(SampleController.class);
	
	@RequestMapping("/star")		// 메소드 안달아주면 GET방식(기본)
	public void basic() {
		logger.info("basic1.........");
		log.info("basic2.......");
	}
	@RequestMapping("/craft")
	public void basic1() {
		logger.info("basic3.........");
		log.info("basic4.......");
	}
	// basic이라는 값으로 오면, GET방식이든 POST방식으로 온다.
	//http://localhost:9090/study/sample/basic/
	@RequestMapping(value="/basic", method= {RequestMethod.GET,RequestMethod.POST})
	public void basicGet() {
		logger.info("basicGet1.........");
		log.info("basicGet2.......");
		
	}
	// POST방식
	// Method Not Allowed 라는 에러페이지가 뜬다 (405)
	@RequestMapping(value="/basic1", method= {RequestMethod.POST})
	public void basicPost() {
		logger.info("basicPost1.........");
		log.info("basicPost2.......");
		
	}
	// GET방식
	@RequestMapping(value="/basic1", method= {RequestMethod.GET})
	public void basicGet2() {
		logger.info("basicGet3.........");
		log.info("basicGet4.......");
		
	}
	// 오직 Get방식으로만 들어옴
	@RequestMapping("/basicOnlyGet")
	public void basicGet3() {
		logger.info("basic get only get5.........");
		log.info("basic get only get6.......");
		
	}
	// 주소는 같음(그래서 Bean 에러가 뜸)
	// basicOnlyGet1으로 주소 바꾸니까 해결
	@RequestMapping("/basicOnlyGet1")
	public void basicPost2() {
		logger.info("basic get only Post3.........");
		log.info("basic get only Post4.......");
		
	}
	// SampleDTO
	@RequestMapping("/ex01")
	// request.getParameter 일일이 안해줘도 되는 부분..
	public String ex01(SampleDTO sDto) {
		// sDto만 파라미터로 넣으면 오류가 뜬다. (String으로 해야함)
		// logger.info(sDto.toString()); -방법1
		// logger.info("" + sDto); -방법2
		logger.info("" + sDto.getSuperName()); 
		logger.info("" + sDto.getAge()); 
		return "ex01";
		
	}
	// superName : name값인 'dd'만 출력된다.
	// superAge : ager값인 '20'만 출력된다
	// 순서도 맞게 해야한다 (순서 다르게 해서 오류뜸)
	@RequestMapping("/ex02")
	public String ex02(@RequestParam("name") String superName,
						@RequestParam("age") String superAge) {
		//log.info("superAge===" + superAge);
		log.info("superAge===" + superAge);
		log.info("superName===" + superName);
		
		return "ex02";
	}
	// Get매핑방식으로 배열로 받겠다
	@GetMapping("/ex02List")
	// List도 가능
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids ====>" +ids);
		
		log.info("=========for문=============");
		for(String idsStr : ids) {
			log.info(idsStr);
		}
		log.info("==========람다출력법==================");
		ids.forEach(idsStr -> {log.info(idsStr);});
		return "ex02List";
		
	}
	/*
	 * @GetMapping("/ex03List") // 따로 받기 (get으로 일일이 받지말고, for문 이용해서 받기) public
	 * String ex03List(@RequestParam("ids") ArrayList<String> ids) {
	 * 
	 */
		
		/*log.info("ids ====>" +ids);
		log.info(ids.get(0));
		log.info(ids.get(1));
		log.info(ids.get(2));*/
		//return "ex03List";
	
	@GetMapping("/ex02Bean")
	// 객체리스트를 가져오겠다
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos : " +list);
		return "ex02Bean";
	}
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("Todo : " + todo);
		return "ex03";
	}
	/*
	 * @InitBinder 
	 * public void initBinder(WebDataBinder binder) { 
	 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 * binder.registerCustomEditor(java.util.Date.class, 
	 * new CustomDateEditor(dateFormat, false)); 
	 * }
	 * 
	 * 
	 */
	
	@RequestMapping("/sampleModel")
	// 모델은 스프링에만 존재하는 객체
	// 이 방식은 컨트롤러에 값을 넣어주는방식
	
	//public String sampleModel(Model model) {
		//SampleDTO sampleDTO = new SampleDTO("유지수",65);
	public String sampleModel(SampleDTO sampleDTO, Model model)	{
		
		log.info("sampleModel");
		
		// =request.setAttribute와 같은 역할
		// addAttribute를 사용해서 객체를 넘겨줌.
		model.addAttribute(sampleDTO);
		// views 아래에 폴더생성->sample.jsp생성
		return "/sample/sample";
	}
	@GetMapping("/ex04")
	// 기초자료형으로 넘어온 int는 표시가 안됐다. 
	// ModelAttribute 어노테이션을 사용해서 page값을 가져왔다.
	public String ex04(SampleDTO sampleDTO, @ModelAttribute("page") int page) {
		
		log.info("sampleDTO :" +sampleDTO);
		log.info("page:" + page);
		return "/sample/ex04";
	}
	@RequestMapping(value="/doE")
	public String doE(RedirectAttributes rttr) {
		log.info("doE 호출되지만 /doF로 ReDirect.......");
		
		//팝업형으로 노출시킬때 message형식으로 띄워준다. 
		rttr.addFlashAttribute("msg", "doE 리다이렉트 메세지입니다.");
		
		// 브라우저에서 sample/어디 그 역할을 한다. 
		// 그것을 가능하게 해주는 문법이 redirect: 이다.
		return "redirect:/sample/doF";
	}
	// doF생성
	@RequestMapping("/doF")
	public String doF() {
		log.info("doF호출됐다");
		
		return "/sample/redirectAttributeResult";
		
	}
	// void 반환타입일때
	// 내가 호출한 이름 경로로 화면페이지를 찾음. (=return안달아줬을때와 같은 상황)
	@RequestMapping("/ex05")
	public void ex05() {
		log.info("/ex05..");
	}
	
	@RequestMapping("/ex06")
	// Body에 응답하라는 어노테이션 사용하니까 값이 브라우저에 출력된다.
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06..........");
		
		// 임의의 데이터 주입
		SampleDTO dto = new SampleDTO();
		dto.setAge(99);
		dto.setSuperName("유지수");
		
		// 반환타입은 DTO
		return dto;
	}
	@RequestMapping("/ex06_1")
	public @ResponseBody Map<String, SampleDTO> ex06_1(){
		log.info("/ex06_1........");
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(99);
		dto.setSuperName("유지수");
		
		Map<String, SampleDTO> map = new HashMap<>();
		// info라는 String들어옴
		map.put("info", dto);
		
		return map;
	}
	
	@RequestMapping("/ex06_2")
	public @ResponseBody Map<String, List<SampleDTO>> ex06_2(){
		log.info("/ex06_2........");
		List<SampleDTO> list = new ArrayList<>();
		Map<String, List<SampleDTO>> map = new HashMap<>();
		
		SampleDTO dto1 = new SampleDTO();
		dto1.setAge(67);
		dto1.setSuperName("김지수");
		
		list.add(dto1);
		
		SampleDTO dto2 = new SampleDTO();
		dto2.setAge(57);
		dto2.setSuperName("박지수");
		
		list.add(dto2);
		// 이 info안에 회원2명의 정보가 존재.
		map.put("info", list);
		
		return map;
	}
	@RequestMapping("/ex07")
	// String타입으로 반환할것이다
	public ResponseEntity<String> ex07(){
		// 쌍따옴표붙어서 key,value표시 (JSON나오기 이전에 이렇게 어렵게 사용함)
		String msg = "{\"name\":\"유지수\"}";
		
		HttpHeaders header = new HttpHeaders();
		// 콘텐트타입: JSON이라고 알려줌. (맞나?)
		header.add("Content-type", "application/json;charset=UTF-8");
		// 객체 반환한다. msg, header, 상태코드(200 녹색등 감지할수있는 그런거, OK는 200을뜻함.)
		return new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
	}
	// exFileUpload.jsp 확인
	// value주소와 jsp파일명이 같아야한다.
	@RequestMapping(value = "/exFileUpload", method = RequestMethod.GET)
	public void exFileUpload() {
		log.info("/exFileUpload.......");
	}
	@RequestMapping(value = "/exUploadPost", method = RequestMethod.POST)
	// IO가 발생하니까 예외처리 thorws해줬음
	// ArrayList는 배열을 
	public void exUploadPost(ArrayList<MultipartFile> files) throws Exception{
		log.info("/exUploadPost..............");
		// files은 파일덩어리이므로 이제 부분적으로 가져올수있도록 해야한다.
		for(MultipartFile multipartFile : files) {
			log.info("----------------------");
			// 파일명 가져올때 쓰는 객체
			log.info("fileName: "+ multipartFile.getOriginalFilename());
			// 파일 사이즈
			log.info("fileSize: "+ multipartFile.getSize());
		}
		log.info("================람다출력==================");
		// 내마음대로 이름 지어줌 (file(여러개중 한개))
		files.forEach(file-> {
			log.info("----------------------");
			log.info("fileName: "+ file.getOriginalFilename());
			log.info("fileSize: "+ file.getSize());
		});
	}
	
	
	
}
