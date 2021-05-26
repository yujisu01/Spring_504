package com.springstudy.shop.board;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.domain.ReplyDTO;
import com.springstudy.shop.board.domain.ReplyPageDTO;
import com.springstudy.shop.board.service.IReplyService;

@RestController
@RequestMapping("/replies")
/*
 * @AllArgsConstructor // 4.3부터가능 (Autowired생략가능)
 */
public class ReplyController {
	
	@Autowired
	private IReplyService service;
	private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
	
	@PostMapping(value = "/new", 
				consumes = "application/json", 
					// MediaType(~http)
				produces = {MediaType.TEXT_PLAIN_VALUE})
	// json데이터 포맷방식이 이 클래스에 type형태처럼 넘어온다면 자동으로 넘어와서 이 객체에 담아준다
	public ResponseEntity<String> create(@RequestBody ReplyDTO replyDto){
		log.info("ReplyDTO : " + replyDto);
		
		int insertCount = service.register(replyDto);
		log.info("Reply Insert Count : " + insertCount);
			// insertCount가 1이라면 new ResponseEntity여기에 success 이렇게 표시하고
			// 아니면 error뜸.
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) 
								: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	// get방식이지만 post방식으로 넘어가는것처럼 표시
	// bno랑 page 받아와야함
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	// 여러건 들어오니까 DTO로받음
	public ResponseEntity<ReplyPageDTO> getList(
			@PathVariable("page") int page,
			@PathVariable("bno") int bno) {
			Criteria cri = new Criteria(page, 10);
			log.info("get Reply List bno ====> " + bno);
			log.info("cri =====>) " + cri);
			log.info("service.getListPage(cri, bno) =====>) " + service.getListPage(cri, bno));
				// http~: 상태코드
			return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
		}
	
	@GetMapping(value = "/{rno}",
				produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	// 조회
	public ResponseEntity<ReplyDTO> read(@PathVariable("rno")int rno){
		log.info("get:"+rno);
		
		return new ResponseEntity<>(service.read(rno), HttpStatus.OK);
		
	}
	// deleteMapping임..
	@DeleteMapping(value = "/{rno}",
					produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") int rno){
		log.info("remove : " + rno);
		
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
										: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
	// 별도로 메서드지정
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, 
					value = "/{rno}", 
					consumes = "application/json",
					produces = {MediaType.TEXT_PLAIN_VALUE})
	// 수정
	public ResponseEntity<String> modify(
			@RequestBody ReplyDTO replyDto,
			@PathVariable("rno") int rno){
		replyDto.setRno(rno);
		
		log.info("rno ===>" + rno);
		log.info("modify ====>" + replyDto);
		
		// 데이터를 변환시켜서 주입 
		return service.modify(replyDto) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
											 : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}
