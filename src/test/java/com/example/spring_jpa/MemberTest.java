package com.example.spring_jpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
@Transactional
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void beforeTest() {
        for (int i = 0; i < 100; i++) {
            em.persist(new Member(String.valueOf(i),i));
        }
    }

    @Test
    @DisplayName("회원 기능 테스트 01")
    void memberTest01() {
        Member member = new Member("member1");
        em.persist(member);

        Member member1 = em.find(Member.class, 1L);
        System.out.println("member1 = " + member1);
    }


    @Test
    @DisplayName("회원 기능 테스트 02")
    void memberTest02() {
        Member member = new Member("member1");
        em.persist(member);

        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember = " + findMember);
    }


    @Test
    @DisplayName("회원 기능 테스트 03")
    void memberTest03() {
        Member member = new Member("member2");
        em.persist(member);

        em.flush();

        System.out.println("======================");
    }

    @Rollback(value = false)
    @Test
    @DisplayName("회원 기능 테스트 04")
    void memberTest04() {
        Member member = new Member("member3");
        em.persist(member);

        Member findMember = em.find(Member.class, 1L); // 영속성 컨텍스트에서 가져오고
        em.detach(findMember);
        findMember.setName("new Member4");
    }

    @Test
    @DisplayName("회원 기능 테스트 05")
    void memberTest05() {
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        em.persist(member1);
        em.persist(member2);

        member1.setName("new Member1");
        em.detach(member1); // 준영속 -> 1차 캐시, 쓰기 지연 SQL 저장소 데이터 삭제

        Member member = em.find(Member.class, 1L); // 영속성 컨텍스트에 해당 id 을 가진 멤버가 없으면 DB 조회
    }


    @Test
    @Rollback(value = false)
    @DisplayName("회원 기능 테스트 07")
    void memberTest07() {

        // id 가 generatedValue 라면 1차 캐시 없이 바로 jpql 날라감
        Member m1 = new Member("member1");
        em.persist(m1); // 영속 상태
        System.out.println(m1);
        System.out.println("sd");
        m1.setName("new member1");
        em.detach(m1); // 준영속 상태 -> 수정 쿼리 x

        System.out.println(m1);
    }

    @Test
    public void memberTest08() {
        Member member = new Member();
        member.setName("최동근");

        Team team = new Team("팀1");
        em.persist(team);
        member.setTeam(team);

        em.persist(member);
        em.flush();
        em.clear();

        Member findMember = em.getReference(Member.class, member.getId());
        System.out.println("before findMember.getId()");
        System.out.println("findMember.getId() = " + findMember.getId()); // id 는 이미 가지고 있기 때문에 db 조회 x
        System.out.println("findMember.getClass() = " + findMember.getClass());
        System.out.println("before findMember.getName()");
        System.out.println("findMember.getName() = " + findMember.getName());
        System.out.println("findMember.getClass() = " + findMember.getClass());
    }

    @Test
    public void memberTest09() {
        Member member = new Member();
        member.setName("m1");
        Team team = new Team("team1");
        em.persist(team);
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member.getId());
        System.out.println("refMember.getClass() = " + refMember.getClass()); // 프록시 객체
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("findMember.getClass() = " + findMember.getClass()); // 프록시 객체
        System.out.println("findMember == refMember = " + (findMember == refMember));
    }

    @Test
    public void memberTest10() {
        Member member = new Member();
        member.setName("m1");
        Team team = new Team("team1");
        em.persist(team);
        member.setTeam(team);
        em.persist(member);


        Member reference = em.getReference(Member.class, member.getId());
        PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
        persistenceUnitUtil.isLoaded(reference);
    }

    @Test
    public void memberTest11() {
        Member member = new Member();
        member.setName("member1");
        Team team = new Team("team1");
        em.persist(team);
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member reference = em.find(Member.class, member.getId());
        Team refTeam = em.getReference(Team.class, team.getId()); // Access = 필드
        Long id = refTeam.getId();
        System.out.println("id = " + id);
    }

    @Test
    public void memberTest12() {
        Member member = new Member();
        member.setName("member1");
        Team team = new Team("team1");
        em.persist(team);
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Member ref = em.find(Member.class, member.getId());

        System.out.println("ref.getTeam().getClass() = " + ref.getTeam().getClass());
        System.out.println("ref.getTeam().getName() = " + ref.getTeam().getName());
    }

    @Test
    @Rollback(value = false)
    public void memberTest13() {
        Member member = new Member();
        member.setName("member1");
        Team team = new Team("team1");
        member.setTeam(team);
        em.persist(member); // Member 만 영속화

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        em.remove(findMember); // member + team 삭제
    }

    @Test
    public void memberTest14() {
        Member m1 = new Member();
        m1.setName("m1");

        List<Member> resultList
                = em.createQuery("select m from Member m where m.name like '%kim'", Member.class).getResultList();

        System.out.println("resultList = " + resultList);

    }

    @Test
    public void memberTest15() {
        Member m1 = new Member();
        m1.setName("m1");
        Team team = new Team("t1");
        m1.setTeam(team);
        em.persist(m1);


        em.flush();
        em.clear();

        Query query = em.createQuery("select m.id, m.name from Member m");
        List resultList = query.getResultList();

        for (Object o : resultList) {
            Object[] ob = (Object[]) o;
            System.out.println("ob[0] = " + ob[0]);
            System.out.println("ob[1] = " + ob[1]);
        }
    }

    @Test
    public void memberTest16() {

        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .setFirstResult(0)
                .setMaxResults(10)
                .getResultList();

        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void memberTest17() {
        String teamName = "teamA";
        List<Member> resultList = em.createQuery("select m from Member m inner join m.team t " +
                        "where t.name = :teamName",Member.class)
                .setParameter("teamName", teamName)
                .getResultList();

        for (Member member : resultList) {
            System.out.println("member = " + member);
        }
    }
    
    @Test
    public void memberTest18() {
        String query
                = "select m.name, 'HELLO', true from Member m" +
                " where m.type = com.example.spring_jpa.MemberType.MEMBER";

        List resultList = em.createQuery(query)
                .getResultList();

        
        for(Object o: resultList) {
            Object[] result = (Object[]) o;
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);
        }
    }
    
    @Test
    public void memberTest19() {
        String query = 
                "select case when m.age <= 10 then '학생요금' " +
                        "when m.age >= 60 then '경로요금' " +
                        " else '일반 요금' "  +
              "end " +
                      "from Member m";
        
        List<String> result = em.createQuery(query, String.class).getResultList();


        for (Object o : result) {
            System.out.println("o = " + o);
        }
    }

    @Test
    public void memberTest20() {
        String firstQuery = "select m.team from Member m";
        List<Team> resultList = em.createQuery(firstQuery, Team.class).getResultList();
        for (Team team : resultList) {
            System.out.println("team = " + team);
        }
    }
}
