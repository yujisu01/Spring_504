package com.springstudy.shop.board.persistence;

import java.util.List;

import com.springstudy.shop.board.domain.BoardDTO;
import com.springstudy.shop.board.domain.Criteria;

public interface IBoardDAO {

	public void create(BoardDTO bDto) throws Exception;
	// 글조회
	public BoardDTO read(Integer bno) throws Exception;
	// 글수정 (int로 잘 수정됐는지 확인)
	public int update(BoardDTO bDto) throws Exception;
	// 글삭제 (int:확인용)
	public int delete(Integer bno) throws Exception;
	// 글목록보기
	// mybatis는 List타입만 됨. 그래서 List로 받음.
	public List<BoardDTO> listAll(Criteria cri) throws Exception;
	public int getTotalCnt(Criteria cri) throws Exception;
}
