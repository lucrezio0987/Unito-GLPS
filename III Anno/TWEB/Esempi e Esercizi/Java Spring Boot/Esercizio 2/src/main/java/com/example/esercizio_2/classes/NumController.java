package com.example.esercizio_2.classes;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumController {
    @PostMapping("/somma")
    public int somma(@RequestBody Num num) {
        return num.somma();
    }
}
