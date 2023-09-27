public class UpcastingTest {

    public static void main(String[] args) {
        A a;
        B b;
        D d = new D();
        System.out.println(d.stampa());
        b = new D();  // oggetto di tipo D visto come se fosse di tipo B
        System.out.println(b.stampa());
        a = new D();  // idem per A
        System.out.println(a.stampa());
        a = b;        // assegno ad una variabile di tipo A un oggetto D (visto come A)
        System.out.println(a.stampa());

        //b = a; //errore, tipi incompatibili //(cerco di assegnare un A ad un B)
    }
}

class A {

    public String stampa() {
        return "sono un A";
    }
}

class B extends A {

    public String stampa() {
        return "sono un B";
    }
}

class C extends A {

    public String stampa() {
        return "sono un C";
    }
}

class D extends B {

    public String stampa() {
        return "sono un D";
    }
}

class E extends C {

    public String stampa() {
        return "sono un E";
    }
}
