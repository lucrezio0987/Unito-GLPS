public class PrimoEsempio {
    public static void main(String[] args) {
        X xy = new Y();
        X xx = new X();

        xy.m1();				// Stampa Metodo 1 di Y + Metodo 1 di X
        System.out.println("");

        xy.m2();				// Stampa Metodo 2 di X + Metodo 1 di Y + Metodo 1 di X
        System.out.println("");

        xx.m1();				// Stampa Metodo 1 di X
        System.out.println("");

        xx.m2();				// Stampa Metodo 2 di X + Metodo 1 di X
    }
}


class X {
    public void m1() {
        System.out.println("Metodo 1 di X");
    }
    public void m2() {
        System.out.println("Metodo 2 di X");
        this.m1();
    }
}

class Y extends X {
    public void m1() {
        System.out.println("Metodo 1 di Y");
        super.m1();
    }
}


