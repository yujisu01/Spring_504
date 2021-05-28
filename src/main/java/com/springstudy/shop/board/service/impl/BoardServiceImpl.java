package com.springstudy.shop.board.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.springstudy.shop.board.domain.BoardAttachDTO;
import com.springstudy.shop.board.domain.BoardDTO;
import com.springstudy.shop.board.domain.Criteria;
import com.springstudy.shop.board.mapper.BoardAttachMapper;
import com.springstudy.shop.board.persistence.IBoardDAO;
import com.springstudy.shop.board.service.IBoardService;

@Service
public class BoardServiceImpl implements IBoardService{
	// 스프링이 객체를 다 준비해줌
	@Autowired
	private IBoardDAO bDao;
	
	// 서비스-인터페이스-구현체 방식인데, attach는 mapper interface방식으로 걸겠다.
	@Autowired
	private BoardAttachMapper attachMapper;
	
	// bDao와 bDto를 한몸으로 묶어야함 (transaction이용, 둘중하나라도 실패하면 rollback)
	@Transactional
	@Override
	public void register(BoardDTO bDto) throws Exception {
		bDao.create(bDto);
			// attachList가 널값이거나, size가 0보다 작거나 크면
		if (bDto.getAttachList() == null || bDto.getAttachList().size() <=0) {
				// 할일이 없으니까 return
			return;
		}
		// 파일이 여러개 있을수도 있으니 forEach문
		bDto.getAttachList().forEach(attach -> {
				// Bno를 꺼내옴
			attach.setBno(bDto.getBno());
			attachMapper.insert(attach);
		});
	}
	// 트랜잭션 어노테이션에 격리성주입 (정상적으로 커밋된 데이터만 읽어와라)
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BoardDTO read(Integer bno) throws Exception {
		bDao.updateViewCnt(bno);
		return bDao.read(bno);
	}

	@Override
	public boolean modify(BoardDTO bDto) throws Exception {
		// 보통 수정할때 로직 
		attachMapper.deleteAll(bDto.getBno());
		boolean modifyResult = bDao.update(bDto) == 1;
		
		if(modifyResult && bDto.getAttachList().size() > 0) {
			bDto.getAttachList().forEach(attach-> {
				attach.setBno(bDto.getBno());
				attachMapper.insert(attach);
			});
		}
		return modifyResult; 
	}
	@Transactional
	@Override
		// 자식테이블부터 삭제, 부모테이블부터 등록
	public boolean remove(Integer bno) throws Exception {

		attachMapper.deleteAll(bno);
		
		return bDao.delete(bno) == 1;
	}

	@Override
	public List<BoardDTO> listAll(Criteria cri) throws Exception {
		return bDao.listAll(cri);
	}
	@Override
	public int getTotalCnt(Criteria cri) throws Exception {
		return bDao.getTotalCnt(cri);
	}
	@Override
	public List<BoardAttachDTO> getAttachList(int bno) {
		
		return attachMapper.findByBno(bno);
	}

}
