
public class Test {

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.tipo); // STAMPA B
        b.stampaTipo();
    }
}

class A {
    String tipo = "A";
    String getTipo() {return tipo;}
    void stampaTipo () {System.out.println(getTipo());} // STAMPA B B
    //void stampaTipo() {System.out.println(tipo);} // STAMPA B A
    }

class B extends A {
    String tipo = "B";
    //String getTipo() {return tipo;}
}