package com.springstudy.shop;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.domain.ReplyDTO;
import com.springstudy.shop.board.mapper.ReplyMapper;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyMapperTests {
	// test용으로 5개에만 넣어줌.
	private int[] bnoArr = {
			180232,
			180231,
			180230,
			180229,
			180228
			};
	@Autowired 
	private ReplyMapper mapper;

//	@Test
//	public void testMapper() {
//		log.info(mapper);
//	}
	
	
//	@Test
//	public void testCreate() {
//				// for문사용안하고 반복문 돌리기 (1~10까지 forEach문 사용하여 i값 받아오기)
//		IntStream.range(1, 10).forEach(i -> {
//				// Autowired안하고 수동생성하는이유: 여러개 넣을거라서
//				// DTO를 메서드 안에서 생성안해주면 마지막댓글만 들어가게된다.
//			ReplyDTO replyDto = new ReplyDTO();
//				// 수동생성이기때문에 데이터를 넣어줌 (set메서드 사용해서)
//				// 배열에서 0,1,2,3,4 자동으로 뽑아옴. (i=index값)
//			replyDto.setBno(bnoArr[i % 5]);
//			replyDto.setReplytext("댓글test" + i);
//			replyDto.setReplyer("유지수"+i);
//			mapper.insert(replyDto);	// 수동으로 넣어준다
//		});
//			
//	}
	
//	@Test
//	public void testRead() {
//			// 첫번째 댓글 확인
//		int targetRno = 1;
//		ReplyDTO replyDto = mapper.read(targetRno);
//		
//		log.info(replyDto);
//	}
	
//	@Test
//	public void testDelete() {
//		int targetRno = 2;
//		int result = mapper.delete(targetRno);
//		log.info("delete result ===>" + result);
//	}
	
//	@Test
//	public void testUpdate() {
//		int targetRno = 9;
//		
//			// update하면 오류난다. 
//			// 일단 read에 담아서
//		ReplyDTO replyDto = mapper.read(targetRno);
//			// 재정의
//		replyDto.setReplytext("수정테스트");
//		
//		int count = mapper.update(replyDto);
//		log.info("update count ======>"+ count);
//	}
	
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		List<ReplyDTO> replies =mapper.getListWithPaging(cri, bnoArr[4]);
		replies.forEach(reply -> log.info(reply));
	}
	

}
