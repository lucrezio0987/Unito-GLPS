/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liliana
 */
public class TestSMS {

    public static void main(String[] args) {
        SMSusaDefaultImpl sms1 = new SMSusaDefaultImpl(1, "ciao ciao");
        System.out.println(sms1.getText());
        SMSusaComponentsImpl sms2 = new SMSusaComponentsImpl(2, "bye bye");
        System.out.println(sms2.getText());
    }
}
