package com.springstudy.shop.persistence.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springstudy.shop.domain.MemberDTO;
import com.springstudy.shop.persistence.IMemberDAO;
// DAO객체에서 사용함
@Repository
public class MemberDAOImpl implements IMemberDAO{
	@Autowired
	private SqlSession sqlSession;
	
	private static final String namespace = "com.springstudy.shop.memberMapper";
	
	@Override
	public String getTime() {
		
		// 그냥 불러오면 된다.
		return sqlSession.selectOne(namespace + ".getTime");
	}

	@Override
	public void insertMember(MemberDTO mDto) {
		
		// 반환타입이 없다. 
		// mDto넘김
		sqlSession.insert(namespace + ".insertMember", mDto);
		
		
	}

	@Override
	public MemberDTO selMember(String userid) throws Exception {
		
		return sqlSession.selectOne(namespace + ".selMember",userid);
	}

	@Override
	public MemberDTO selLoginInfo(String userid, String userpw) throws Exception {
		Map<String, Object> paramMap= new HashMap<>();
		paramMap.put("userid", userid);
		paramMap.put("userpw", userpw);
		return sqlSession.selectOne(namespace + ".selLoginInfo" , paramMap);
	}

	@Override
	public int updateMember(MemberDTO mDto) throws Exception {
		return sqlSession.update(namespace + ".updateMember", mDto);
	}

	@Override
	public int deleteMember(String userid) throws Exception {
		// 단일객체얻음 
		return sqlSession.delete(namespace + ".deleteMember", userid);
		
	}

}
