
import java.util.Comparator;

public class EsempioLambda {
	public static void main(String[] args) {
		// posso togliere il tipo dei parametri perche'
		// definito dall'interface Comparator
		Comparator<String> c =
		// posso togliere anche le {} e il return,
		// lasciando solo l'istruzione del body
		// (solo in caso di body con una sola istruzione
				(s1, s2) -> s1.compareTo(s2);
		int ris = c.compare("ciao", "ciao");
		System.out.println(ris);
		System.out.println(c.compare("ciao1", "ciao2"));
	}
}