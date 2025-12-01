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

    private String name;

    private LocalDate birthDate;

    private String email;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @OneToMany(mappedBy="member", cascade=CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    // private 생성자 (외부에서 직접 생성 방지)
    private Member(String name, LocalDate birthDate, String email, Sex sex) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.sex = sex;
    }

    public static Member of(String name, LocalDate birthDate, String email, Sex sex) {
        return new Member(name, birthDate, email, sex);
    }
}