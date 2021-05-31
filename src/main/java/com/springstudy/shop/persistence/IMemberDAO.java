package com.springstudy.shop.persistence;

import com.springstudy.shop.member.domain.MemberDTO;

public interface IMemberDAO {

	public String getTime();
	// 외부로부터 파라미터로 주입받을거니까 매개변수 만들어줘야한다. (외부로부터 데이터를 넘겨받을수 있는 유일한방법)
	public void insertMember(MemberDTO mDto );
	public MemberDTO selMember(String userid) throws Exception;
	public MemberDTO selLoginInfo(String userid, String userpw) throws Exception;
	public int updateMember(MemberDTO mDto) throws Exception; 
	public int deleteMember(String userid) throws Exception;
}
