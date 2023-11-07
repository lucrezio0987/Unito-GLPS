package it.unito.iumtweb.springboot.character;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
public class CharacterController {

    @PostMapping("/calculateAge")
    public Character calculateAge(@RequestBody Character character) {
        // Calculate age
        LocalDate today = LocalDate.now();
        int age = Period.between(character.getDob(), today).getYears();
        character.setAge(age);
        return character;
    }
}
