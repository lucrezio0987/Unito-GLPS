package com.example.sommaserver.classes;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumController {
    @PostMapping("/somma")
    public String somma(@RequestBody Num num) {
        return Integer.toString(num.somma());
    }
}
