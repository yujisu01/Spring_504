package com.springstudy.shop.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.domain.ReplyDTO;

public interface ReplyMapper {
	public String getTime2();
	public int insert(ReplyDTO reply);
	public ReplyDTO read(int rno);
	public int delete(int rno);		//rno값 넘겨받음
	public int update(ReplyDTO replyDto);
	public List<ReplyDTO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") int bno
			);
	public int getCountByBno(int bno);	// 댓글 
}
