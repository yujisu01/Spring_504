package com.springstudy.shop.board.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springstudy.shop.board.domain.BoardDTO;
import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.persistence.IBoardDAO;

@Repository
public class BoardDAOImpl implements IBoardDAO{
	@Autowired
	private SqlSession session;
	
	/*
	 * private static final String namespace =
	 * "com.springstudy.shop.board.boardMapper";
	 */

	@Override
	public void create(BoardDTO bDto) throws Exception {
		// mapper에서 달아준 name : create
		// session의 insert
		session.insert("BoardMapper.create", bDto);
		
	}

	@Override
	public BoardDTO read(Integer bno) throws Exception {
		
		return session.selectOne("BoardMapper.read", bno);
	}

	@Override
	public int update(BoardDTO bDto) throws Exception {
		
		return session.update("BoardMapper.update",bDto);
	}

	@Override
	public int delete(Integer bno) throws Exception {
		return session.delete("BoardMapper.delete",bno);
	}

	@Override
	public List<BoardDTO> listAll(Criteria cri) throws Exception {
		// 파라미터가 없으니까 listAll만 넘긴다.
		return session.selectList("BoardMapper.getListWithPaging", cri);
	}

	@Override
	public int getTotalCnt(Criteria cri) throws Exception {
		// 파라미터로 cri넘김
		return session.selectOne("BoardMapper.getTotalCnt", cri);
}

	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		session.update("BoardMapper.updateViewCnt",bno);
	}

	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("bno", bno);
		paramMap.put("amount", amount);
		
		session.update("BoardMapper.updateReplyCnt", paramMap);
	}

	
}
