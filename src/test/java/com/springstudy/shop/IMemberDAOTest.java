package com.springstudy.shop;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springstudy.shop.domain.MemberDTO;
import com.springstudy.shop.persistence.IMemberDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class IMemberDAOTest {
	
	private static final Logger logger = LoggerFactory.getLogger(IMemberDAOTest.class);
	
	@Autowired
	private IMemberDAO mDao;
	
	@Test
	public void testTime() throws Exception {
		logger.info("mDao.getTime() ==>" + mDao.getTime());
	}
//	@Test
//	public void selMember() throws Exception {
//		MemberDTO mDto = mDao.selMember("user01");
//		
//		logger.info(mDto.toString());
//	}
//	@Test
//	public void selLoginInfo() throws Exception{
//		MemberDTO mDto = mDao.selLoginInfo("user02", "user02");
//		
//		logger.info(mDto.toString());
//	}
//	
//	  @Test 
//	  public void testInsertMember() throws Exception{ // DTO넘겨줘야함 
//	  MemberDTO mDto = new MemberDTO(); 
//	  mDto.setUserid("user06"); 
//	  mDto.setUserpw("user06");
//	  mDto.setUsername("USER06"); 
//	  mDto.setEmail("user06@naver.com");
//	  
//	  mDao.insertMember(mDto);
//	  
//	  }

	@Test
	public void testUpdateMember() throws Exception{
		MemberDTO mDto = new MemberDTO();
		mDto.setUserid("user05");
		mDto.setUserpw("12");
		mDto.setEmail("user05@google.net");
		
		mDao.updateMember(mDto);
	}
	
	@Test
	public void testDeleteMember() throws Exception{
		mDao.deleteMember("user04");
	}

}
