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
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@WebAppConfiguration
public class MockMvcTest {
	// Logger생성
	private static final Logger logger = LoggerFactory.getLogger(MockMvcTest.class);
	
	// 속성(field), setter method, constructor(생성자)에서 사용하며 Type에 따라 알아서 Bean을 주입 해준다.
	// 무조건적인 객체에 대한 의존성을 주입시킨다.
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	
	// org.junit
	@Before
	public void setup() {
		// 정보만 주입시키고 .build를 넣어주면 빌드가 끝난다 (아그렇구나 정도로만...)
		// jQuery의 체이닝기법과 비슷하게 쓸수있다. ~메소드. ~메소드. 
		// Java에서는 빌더패턴 이라고 한다. 
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	@Test
	public void test() throws Exception {
		// 컨트롤러 경로를 잡아주는곳..
		// 내가 테스트할 경로를 잡아준다.
		// sample/ex04는 파라미터가 3개필요하다.
		String resultPage = mockMvc
							.perform(MockMvcRequestBuilders.get("/sample/ex04")
							.param("superName","유지수")
							.param("age", "61")
							.param("page", "9")).andReturn().getModelAndView().getViewName();
		
		// page보이게 
		logger.info(resultPage);
	}
	
	
}
