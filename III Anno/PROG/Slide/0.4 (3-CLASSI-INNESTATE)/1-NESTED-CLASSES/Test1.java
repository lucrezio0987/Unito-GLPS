/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liliana
 */
public class Test1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClasseEsterna ce = new ClasseEsterna(10);
        System.out.println("Valore del dato di oggetto ClasseEsterna: " + ce.getVal());
        ClasseEsterna.ClasseInterna ci = ce.getCi();
        System.out.println("Valore del dato di oggetto ClasseInterna: " + ci.getValInterno());
        ce.setVal(30);
        System.out.println("Valore del dato di oggetto ClasseInterna dopo la modifica "+ ci.getValInterno());
    }

}

class ClasseEsterna {
    private int val;
    private ClasseInterna ci;

    public ClasseEsterna(int v) {
        val = v;
        ci = new ClasseInterna(v);
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
        ci.valInterno = val;
    }

    public ClasseInterna getCi() {
        // se dichiaro ClasseInterna private -> errore in Test1 perche' non visibile
        return ci;
    }

    class ClasseInterna {
        private int valInterno;

        public ClasseInterna(int v) {
            valInterno = v;
        }

        public int getValInterno() {
            return valInterno;
        }
    }
}
