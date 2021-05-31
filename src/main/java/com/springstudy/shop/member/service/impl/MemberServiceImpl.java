package com.springstudy.shop.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstudy.shop.member.domain.MemberDTO;
import com.springstudy.shop.member.mapper.MemberMapper;
import com.springstudy.shop.member.service.IMemberService;

@Service
public class MemberServiceImpl implements IMemberService{
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public MemberDTO login(MemberDTO mDto) {
		return mapper.login(mDto);
	}

}
