
import java.util.Comparator;

public class EsempioLambda1 {
	public static void main(String[] args) {
		// posso togliere il tipo dei parametri perche'
		// definito dall'interface Comparator
		Comparator<String> c =
				(s1, s2) ->
					{return s1.compareTo(s2); };
		int ris = c.compare("ciao", "ciao");
		System.out.println(ris);
		System.out.println(c.compare("ciao1", "ciao2"));
	}
}