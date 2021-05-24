package com.springstudy.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Data를 달아주면 get,set,ToString 생성됨
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {

	private String superName;
	private int age;
	
}
