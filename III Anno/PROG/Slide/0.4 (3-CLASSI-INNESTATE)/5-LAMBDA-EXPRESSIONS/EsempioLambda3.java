interface Prova {
	public void stampa();
}

public class EsempioLambda3 {
	public static void main(String[] args) {
		Prova p = () -> {System.out.println("CIAO");};
		p.stampa();
	}
}