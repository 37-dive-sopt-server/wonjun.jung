package org.sopt.domain;

import java.time.LocalDate;

public class Member {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private Sex sex;

    public Member(Long id, String name, LocalDate birthDate, String email, Sex sex) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.sex = sex;
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