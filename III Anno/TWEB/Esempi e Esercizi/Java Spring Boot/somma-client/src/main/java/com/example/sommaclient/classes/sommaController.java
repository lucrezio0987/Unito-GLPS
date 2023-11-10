package com.example.sommaclient.classes;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class sommaController {
    @PostMapping("/somma")
    public int somma(@RequestBody Num num) {
        String res = new RestTemplate().postForObject("http://localhost:8081/somma", num, String.class);
        return Integer.parseInt(res);
    }
}
