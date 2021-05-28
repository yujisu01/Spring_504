package com.springstudy.shop.board.mapper;

import java.util.List;

import com.springstudy.shop.board.domain.BoardAttachDTO;

public interface BoardAttachMapper {
	public void insert(BoardAttachDTO boardAttachDto);
	public void delete(String uuid);
		// 등록되어있는 파일들도 추출해와야 함
	public List<BoardAttachDTO> findByBno(int bno);
	
	public void deleteAll(int bno);
	
}
