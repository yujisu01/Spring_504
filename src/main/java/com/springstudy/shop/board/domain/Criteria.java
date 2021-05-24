package com.springstudy.shop.board.domain;

/*import org.springframework.web.util.UriComponentsBuilder;*/

import lombok.Data;

@Data
public class Criteria {
	private int pageNum;
	private int amount;
	private String type;	// 검색어
	private String keyword;
	
	// Default생성자
	public Criteria() {
		// 1번째 리스트에 게시글 10개씩 보여주겠다.
		this(1, 10);
	}
	public Criteria(int pageNum, int amount) {
		// 맨처음 넘어오는 것은 pageNum이다.
		this.pageNum = pageNum;
		this.amount = amount;
	}
	// string배열로 잡음
	// mybatis에 넘겨줌 
	public String[] getTypeArr() {
		// type이 null이면 new String[] 으로 표기
		// null 이 아니라면 공백기준으로 split(자르기)해라.
		return type == null? new String[]{} : type.split("");
	}
//	public String getListLink() {
//		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
//				.queryParam("pageNum", this.pageNum)
//				.queryParam("amount", this.getAmount())
//				.queryParam("type", this.getType())
//				.queryParam("keyword", this.getKeyword());
//				
//		return builder.toUriString();	
//	}

}
