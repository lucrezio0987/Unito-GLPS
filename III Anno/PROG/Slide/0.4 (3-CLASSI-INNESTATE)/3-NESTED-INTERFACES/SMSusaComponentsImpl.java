/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liliana
 */
public class SMSusaComponentsImpl implements Message {
    private Message.MsgImpl msg;

    public SMSusaComponentsImpl(int dest, String txt) {
        msg = new Message.MsgImpl(dest, txt);
    }
    public String getText() {
        return msg.getText();
    }
    public String getDest() {
        return msg.getDest();
    }
}
