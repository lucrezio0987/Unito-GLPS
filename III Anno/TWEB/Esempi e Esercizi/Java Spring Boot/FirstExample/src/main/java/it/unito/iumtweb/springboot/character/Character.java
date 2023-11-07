package it.unito.iumtweb.springboot.character;

import java.time.LocalDate;
import java.time.Period;

public class Character {
    private String name;
    private String surname;
    private LocalDate dob;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        // Calculate age
        LocalDate today = LocalDate.now();
        this.age = Period.between(getDob(), today).getYears();
    }
}
