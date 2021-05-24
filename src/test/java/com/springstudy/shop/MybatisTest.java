package com.springstudy.shop;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Log4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class MybatisTest {
   
   @Autowired   
   private SqlSessionFactory sqlFactory;

   @Test //Annotation을 써줘야 스프링이 알아먹음. Annotation 은 servlet-context.xml에 써줘야댐
   public void testFactory() {
      log.info(sqlFactory);
   }
   
   @Test
   public void testSession() {
      try (SqlSession session = sqlFactory.openSession()) {
         log.info(session);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}