package it.torino.adderserver.server1.adder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class AdderController {

    @PostMapping("/add")
    public String sum(@RequestBody NumberSumRecord record) {

            // Send the record to server 2
            String result = new RestTemplate().postForObject("http://localhost:8081/sum", record, String.class);

            // Return the result to the axios query
            return result;
        }

    record NumberSumRecord (
        int number1,
        int number2
    ){}
}

