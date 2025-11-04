package org.sopt.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
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
    private List<Article> articles;

    protected Member() {}

    // private 생성자 (외부에서 직접 생성 방지)
    private Member(Long id, String name, LocalDate birthDate, String email, Sex sex) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.sex = sex;
    }

    public static Member createNew(String name, LocalDate birthDate, String email, Sex sex) {
        return new Member(null, name, birthDate, email, sex);
    }

    public static Member of(Long id, String name, LocalDate birthDate, String email, Sex sex) {
        return new Member(id, name, birthDate, email, sex);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public Sex getSex() {
        return sex;
    }

    public List<Article> getArticles() {
        return articles;
    }
}