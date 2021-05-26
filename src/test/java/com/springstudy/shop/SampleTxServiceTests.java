package com.springstudy.shop;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springstudy.shop.board.service.ISampleTxService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class SampleTxServiceTests {

	@Autowired
	private ISampleTxService service;
	private static final Logger log = LoggerFactory.getLogger(SampleTxServiceTests.class);
	@Test
	public void testLong() {
		String str = "최엘비 최엘비 최엘비";
		
		log.info(""+str.getBytes().length);
		
		service.addData(str);
	}

}
