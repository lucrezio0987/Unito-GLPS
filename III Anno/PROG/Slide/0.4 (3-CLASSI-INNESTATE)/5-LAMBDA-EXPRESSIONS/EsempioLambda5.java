interface Prova {
	public void stampa(String s);
}

public class EsempioLambda5 {
	public static void main(String[] args) {
		Prova p = messaggio -> {System.out.println(messaggio);};
		MyClass m = new MyClass();
		m.metodo(p);
	}
}

class MyClass {
	void metodo(Prova p) {
		System.out.println("Sto per richiamare il metodo stampa");
		p.stampa("Metodo invocato!");
	}
}