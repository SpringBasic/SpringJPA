package com.example.spring_jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("회원 기능 테스트 01")
    void memberTest01() {
        Member member = new Member("member1");
        em.persist(member);

        Member member1 = em.find(Member.class, 1L);
        System.out.println("member1 = " + member1);
    }
}
