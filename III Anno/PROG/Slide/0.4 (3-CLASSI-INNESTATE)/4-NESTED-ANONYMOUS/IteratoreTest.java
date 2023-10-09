/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

interface Iteratore {

    boolean hasNext();

    Object next();
}

class Collezione {

    private Object[] array;

    public Collezione(Object[] elenco) {
        array = elenco;
    }

    public Iteratore getIteratore() {

        class Iter implements Iteratore {

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
        } // end Iter
        return new Iter();
    }
}// end Collezione

public class IteratoreTest {

    public static void main(String[] args) {
        String[] parole = {"ciao", "hello", "bye", "arrivederci"};
        Collezione ii = new Collezione(parole);
        Iteratore it = ii.getIteratore();
        while (it.hasNext()) {
            String s = (String) it.next();
            System.out.println(s);
        }
    }

}
