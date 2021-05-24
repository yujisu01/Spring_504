package com.springstudy.shop;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springstudy.shop.board.domain.BoardDTO;
import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.persistence.IBoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class IBoardDAOTest {
	private static final Logger logger = LoggerFactory.getLogger(IBoardDAOTest.class);

	@Autowired
	private IBoardDAO bDao;
	
//	@Test
//	public void testCreate() throws Exception {
//		BoardDTO bDto = new BoardDTO();
//		// 하드코딩으로 주입
//		bDto.setTitle("....");
//		bDto.setContent("....");
//		bDto.setWriter("user03");
//		// 호출
//		bDao.create(bDto);
//	}
//	
//	@Test
//	public void testRead() throws Exception{
//		logger.info(bDao.read(1).toString());
//	}
//	
//	@Test
//	public void testUpdate() throws Exception{
//		BoardDTO bDto = new BoardDTO();
//		bDto.setBno(1);
//		bDto.setTitle("수정된 글입니다");
//		bDto.setContent("수정된 글입니다.");
//		
//		bDao.update(bDto);
//	}

//	@Test
//	public void testDelete() throws Exception{
//		bDao.delete(1);
//	}
	
	
//	  @Test 
//	  public void testListCriteria() throws Exception{
//		  Criteria cri = new Criteria();
//			cri.setPageNum(1);
//			cri.setAmount(10);
//			
//			List<BoardDTO> list = bDao.listAll(cri);
//			list.forEach(board -> logger.info(board.getBno() + " : " + board.getContent()));
//	  }
	
	@Test
	public void testSearch() throws Exception{
		Criteria cri = new Criteria();
		cri.setKeyword("10");
		/* cri.setType("TC"); */
		// content 로만 확인할때 (mapper문의 OR가 안나타남)
		cri.setType("C"); 
		
		// list로 넘어오니까 list로 받음
		List<BoardDTO> list = bDao.listAll(cri);
		// board:임의로 써줘도됨
		logger.info("===========람다===============");
		list.forEach(board->logger.info(""+board));
	}
	
	 
}
