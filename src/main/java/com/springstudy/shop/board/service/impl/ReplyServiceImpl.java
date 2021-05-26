package com.springstudy.shop.board.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.domain.ReplyDTO;
import com.springstudy.shop.board.domain.ReplyPageDTO;
import com.springstudy.shop.board.mapper.ReplyMapper;
import com.springstudy.shop.board.persistence.IBoardDAO;
import com.springstudy.shop.board.service.IReplyService;
@Service
public class ReplyServiceImpl implements IReplyService{
	@Autowired
	private ReplyMapper mapper;
	
	@Autowired
	private IBoardDAO boardDao;
	
	private static final Logger log = LoggerFactory.getLogger(ReplyServiceImpl.class);
	
	@Transactional
	@Override
	public int register(ReplyDTO replyDto) {
			// 해당되는 경로로 문제없이 나오는지 확인하는 용도...
		log.info("reply register=====>" + replyDto);
		try {
			boardDao.updateReplyCnt(replyDto.getBno(), +1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapper.insert(replyDto);
	}
	
	// 댓글상세
	@Override
	public ReplyDTO read(int rno) {
		
		return mapper.read(rno);
	}

	@Override
	public int modify(ReplyDTO replyDto) {
		
		return mapper.update(replyDto);
	}

	@Transactional
	@Override
	public int remove(int rno) {
		ReplyDTO replyDto = mapper.read(rno);
		try {
			boardDao.updateReplyCnt(replyDto.getBno(), -1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return mapper.delete(rno);
	}

	@Override
	public List<ReplyDTO> getList(Criteria cri, int bno) {
		
		return mapper.getListWithPaging(cri, bno);
	}

	@Override
	public ReplyPageDTO getListPage(Criteria cri, int bno) {
		return new ReplyPageDTO(mapper.getCountByBno(bno), 
								mapper.getListWithPaging(cri, bno));
	}

}
