package org.sopt.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "idx_member_name", columnList = "name")
})
public class Member {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy="member", cascade=CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public void update(String name, LocalDate birthDate, String email, Sex sex) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.sex = sex;
    }

    public static Member of(String name, LocalDate birthDate, String email, Sex sex) {
        Member member = new Member();
        member.name = name;
        member.birthDate = birthDate;
        member.email = email;
        member.sex = sex;
        return member;
    }
}