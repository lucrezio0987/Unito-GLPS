/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liliana
 */
public interface Message {
    public String getText();
    public String getDest();

    static class MsgImpl {
        protected int destinatario;
        protected String txt;

        public MsgImpl(int destinatario, String testo) {
            this.destinatario = destinatario;
            txt = testo;
        }
        public String getText(){
            return txt;
        }
        public String getDest() {
            return Integer.toString(destinatario);
        }
    }

}
