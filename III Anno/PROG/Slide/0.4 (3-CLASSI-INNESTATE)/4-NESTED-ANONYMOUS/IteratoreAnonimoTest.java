/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author liliana
 */
interface Iteratore2 {

    boolean hasNext();

    Object next();
}

class Collezione2 {

    private Object[] array;

    public Collezione2(Object[] elenco) {
        array = elenco;
    }

    public Iteratore2 getIteratore() {
        return new Iteratore2() {
            private int pos = 0;
            public boolean hasNext() {
                return (pos < array.length);
            }
            public Object next() {
                if (pos < array.length) {
                    pos++;
                    return array[pos - 1];
                } else {
                    return null;
                }
            }
        };
    }
}// end Collezione

public class IteratoreAnonimoTest {

    public static void main(String[] args) {
        String[] parole = {"ciao", "hello", "bye", "arrivederci"};
        Collezione2 ii = new Collezione2(parole);
        Iteratore2 it = ii.getIteratore();
        while (it.hasNext()) {
            String s = (String) it.next();
            System.out.println(s);
        }
    }

}
