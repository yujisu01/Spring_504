package com.springstudy.shop.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data

public class TodoDTO {

	private String title;
	// 여기에도 적용되는 어노테이션이 있다
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date dueDate;
}
