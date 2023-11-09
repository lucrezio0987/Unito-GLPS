package com.example.provaesercizio1.character;

import java.time.LocalDate;
import java.time.Period;

public class Character {
    private String name;
    private String surname;
    private LocalDate dob;
    private int age;

    public String getName()                 { return name; }
    public String getSurname()              { return surname; }
    public LocalDate getDob()               { return dob; }
    public int getAge()                     { return age; }

    public void setName(String name)        { this.name = name; }
    public void setSurname(String surname)  { this.surname = surname; }
    public void setDob(LocalDate dob)       { this.dob = dob; }
    public void calculateAge()              { this.age = Period.between(getDob(), LocalDate.now()).getYears(); }
}
