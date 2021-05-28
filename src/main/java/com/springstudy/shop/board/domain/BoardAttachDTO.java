package com.springstudy.shop.board.domain;

import lombok.Data;

@Data
public class BoardAttachDTO {
	private String uuid;
	private String uploadPath;
	private String fileName;
	private boolean filetype;
	private int bno;
	

}