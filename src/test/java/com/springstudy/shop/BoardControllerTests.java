package com.springstudy.shop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
public class BoardControllerTests {
	private static final Logger logger = LoggerFactory.getLogger(BoardControllerTests.class);
	
	@Autowired
	private WebApplicationContext ctx;
	
	// MockMVC생성
	private MockMvc mockMvc;
	
	// 선행작업 
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testList() throws Exception {
		// "" =스트링처리
		// 실제브라우저에서 호출할 주소 
		// getModelAndView : 객체와 화면을 처리 (MVC에 있는것임)
		logger.info("" +mockMvc
				.perform(MockMvcRequestBuilders
				.get("/board/list"))
		.andReturn()
		.getModelAndView()
		.getModelMap() );
	}
//	@Test
//	public void testRegister() throws Exception {
//
//		String resultPage = mockMvc
//							.perform(MockMvcRequestBuilders.post("/board/register")
//							.param("title","글번호테스트....")
//							.param("content", "테스트 글내용 ")
//							.param("writer", "user01"))
//							.andReturn()
//							.getModelAndView()
//							.getViewName();
//		
//		logger.info(resultPage);
//	}
	
	@Test
	public void testRead() throws Exception{
		logger.info(""+mockMvc.perform(MockMvcRequestBuilders.
				get("/board/list").
				param("pageNum", "2").
				param("amount", "20")).
				andReturn().
				getModelAndView().
				getModelMap());
	}
//	@Test
//	public void testModify() throws Exception{
//		logger.info(""+mockMvc.perform(MockMvcRequestBuilders.
//				post("/board/modify").
//				param("bno", "3").
//				param("title", "수정된 테스트 글제목").
//				param("content", "수정된 테스트 글내용").
//				// writer는 수정권한이 아님.
//				param("writer", "user01")).
//				andReturn().
//				getModelAndView().
//				getModelMap());
//	}
//	@Test
//	public void testRemove() throws Exception{
//		logger.info(""+mockMvc.perform(MockMvcRequestBuilders.
//				post("/board/remove").
//				param("bno", "23")).
//				andReturn().
//				getModelAndView().
//				getModelMap());
//	}
	

}
