public class EredVar {

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.tipo); // stampa B
        b.stampa(); // stampa A
    }
}

class A {
    String tipo = "A";
    void stampa() {
        System.out.println(tipo);
    }
}

class B extends A {
    String tipo = "B";
    void stampa() {
	        System.out.println(tipo);
    }
}
