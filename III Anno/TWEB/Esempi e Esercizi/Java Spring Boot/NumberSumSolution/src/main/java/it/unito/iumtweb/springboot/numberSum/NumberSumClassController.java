package it.unito.iumtweb.springboot.numberSum;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberSumClassController {

    @PostMapping("/sumNumber")
    public int calculateAge(@RequestBody NumberSumClass numberSumClass) {
        numberSumClass.computeSum();
        return numberSumClass.getSum();
    }
}
