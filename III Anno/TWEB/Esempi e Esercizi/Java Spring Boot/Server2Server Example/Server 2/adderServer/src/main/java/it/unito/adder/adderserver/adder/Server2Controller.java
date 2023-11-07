package it.unito.adder.adderserver.adder;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Server2Controller {

    @PostMapping("/sum")
    public String sum(@RequestBody NumberSumRecord record) {
        int result = record.number1 + record.number2;
        return String.valueOf(result);
    }

    record NumberSumRecord (
        int number1,
        int number2
    ){}
}
