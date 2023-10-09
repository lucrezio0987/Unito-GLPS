
import java.util.Comparator;

public class EsempioSenzaLambda {
	public static void main(String[] args) {
					// con uso di classe anonima innestata
		Comparator<String> c = new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		};
		int ris = c.compare("ciao", "ciao");
		System.out.println(ris);
		System.out.println(c.compare("ciao1", "ciao2"));
	}
}