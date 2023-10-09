
import java.util.Comparator;

public class EsempioLambda0 {
	public static void main(String[] args) {
		// con lambda - versione base
		Comparator<String> c =
				(String s1, String s2) ->
					{return s1.compareTo(s2); };
		int ris = c.compare("ciao", "ciao");
		System.out.println(ris);
		System.out.println(c.compare("ciao1", "ciao2"));
	}
}