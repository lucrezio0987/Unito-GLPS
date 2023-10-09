interface Prova {
	public void stampa(String s);
}

public class EsempioLambda4 {
	public static void main(String[] args) {
		Prova p = messaggio -> {System.out.println(messaggio);};
		p.stampa("CIAO!!");
	}
}