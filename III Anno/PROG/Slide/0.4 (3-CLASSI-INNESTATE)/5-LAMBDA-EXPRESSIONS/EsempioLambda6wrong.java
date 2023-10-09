interface Prova {
	public void stampa(String s);
	public void saluta(int n);
}


public class EsempioLambda6wrong {
	public static void main(String[] args) {
		Prova p = messaggio -> {System.out.println(messaggio);};
		// multiple non-overriding abstract methods found in interface Prova
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