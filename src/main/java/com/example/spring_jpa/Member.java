package com.example.spring_jpa;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@ToString(of = {"id","name"})
@Getter@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public Member(Long id , String name) {
        this.id = id;
        this.name = name;
    }

    public Member(String name) {
        this.name = name;
    }
}
