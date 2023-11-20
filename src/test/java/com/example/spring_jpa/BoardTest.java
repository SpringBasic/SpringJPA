package com.example.spring_jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class BoardTest {

    @PersistenceContext
    EntityManager em;
    @Test
    @DisplayName("기본 키 생성 전략 테스트")
    void generatePrimaryKeyTest(){

        Board b1 = new Board("board1");
        Board b2 = new Board("board2");
        em.persist(b1);
        em.persist(b2);


     }
}
