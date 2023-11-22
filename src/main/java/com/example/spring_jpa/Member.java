package com.example.spring_jpa;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static com.example.spring_jpa.MemberType.*;

@NoArgsConstructor
@ToString(of = {"id","name"})
@Getter@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    private int age;

    @Enumerated(EnumType.STRING)
    private MemberType type;

    public Member(String name, int age) {
        this.id = id;
        this.name = name;
        this.type = MEMBER;
        this.age = age;
    }

    public Member(String name) {
        this.name = name;
    }
}
