package com.springstudy.shop.member.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// domain: 하나의 온전한 시스템 단위 
// 예> 회원, 상품, 배송
// 개발자에게 도메인 클래스는 특정 테이블과 유사한 속성을 가지는 클래스를 의미한다.

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MemberDTO {
	private String userid;
	private String userpw;
	private String username;
	private String email;
	private Timestamp regdate;
	private Timestamp updatedate;
}
