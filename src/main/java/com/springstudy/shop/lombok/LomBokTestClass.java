package com.springstudy.shop.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LomBokTestClass {
	private String id;
	private String name;
	private int age;
	private char gender;
	private boolean check;
	private double height;
}
