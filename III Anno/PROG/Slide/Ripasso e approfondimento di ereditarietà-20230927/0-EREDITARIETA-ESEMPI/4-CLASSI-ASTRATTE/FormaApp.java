public class FormaApp {

    public static void main(String[] args) {
        Rettangolo r = new Rettangolo();
        Linea l = new Linea();
        r.disegna();
        l.disegna();
    }

}

abstract class Forma {
    private String nome;

    public Forma(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    abstract public void disegna();
}

class Rettangolo extends Forma {
    public Rettangolo() {
        super("Rettangolo");
    }
    public void disegna() {
        System.out.println("Disegno un rettangolo");
    }
}

class Linea extends Forma {
    public Linea() {
        super("Linea");
    }
    public void disegna() {
        System.out.println("Disegno una linea");
    }
}


