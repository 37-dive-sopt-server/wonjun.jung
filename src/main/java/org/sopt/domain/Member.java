package org.sopt.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private Sex sex;

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
}