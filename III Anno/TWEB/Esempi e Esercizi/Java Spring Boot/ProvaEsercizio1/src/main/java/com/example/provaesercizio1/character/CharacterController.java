package com.example.provaesercizio1.character;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CharacterController {

    @PostMapping("/calculateAge")
    public Character calculateAge(@RequestBody Character character) {
        character.calculateAge();
        return character;
    }
}
