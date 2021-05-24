package com.springstudy.shop.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class SampleDTOList {
	// private으로 DTO를 받았다. List가 SampleDTO를 품고있다. 
	// 객체리스트
	// SampleDTO값을 꺼내올수 있다..
	private List<SampleDTO> list;
	
	public SampleDTOList() {
		list = new ArrayList<>();
	}
}
