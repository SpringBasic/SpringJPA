package com.example.spring_jpa;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@ToString(of = {"name"})
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members;

    @Builder
    public Team(String name) {
        this.name = name;
    }
}
