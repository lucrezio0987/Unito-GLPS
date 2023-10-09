
import java.util.Comparator;

public class EsempioLambda2 {
	public static void main(String[] args) {
		// senza tipo dei parametri
		Comparator<String> c =
		// con body semplificato
				(s1, s2) -> s1.compareTo(s2);
				//(s1, s2) -> return 1; sbagliato perche' non funzionale
		int ris = c.compare("ciao", "ciao");
		System.out.println(ris);
		System.out.println(c.compare("ciao1", "ciao2"));
	}
}