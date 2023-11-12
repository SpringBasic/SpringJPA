package com.example.spring_jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.beans.PersistenceDelegate;

public class JpaMain {
    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 여러 쓰레드가 동시에 접근해도 안전하여, 쓰레드 간 공유해도 문제 없음
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 엔티티 매니저는 여러 쓰레드가 동시에 접근하면 동시성 문제가 발생해서, 쓰레드 간 공유하면 x
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member("member1");
        em.persist(member);
        tx.commit();

        emf.close();
        em.close();
    }
}
