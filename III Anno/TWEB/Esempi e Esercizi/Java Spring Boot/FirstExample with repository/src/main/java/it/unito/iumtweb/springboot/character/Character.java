package it.unito.iumtweb.springboot.character;
import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Character {
    @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
    @Column(nullable=false, name = "first_name", )
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

    public void setAge(int age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
