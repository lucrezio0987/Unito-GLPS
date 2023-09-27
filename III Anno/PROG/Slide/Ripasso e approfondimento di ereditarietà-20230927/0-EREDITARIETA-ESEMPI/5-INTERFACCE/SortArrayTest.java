
import java.util.*;

public class SortArrayTest {

   public static void main(String args[]) {
   	String[] elenco = new String[5];
	elenco[0] = "Giovanni";
	elenco[1] = "Anna";
	elenco[2] = "Maria";
	elenco[3] = "Ruggero";
	elenco[4] = "Antonio";

	Arrays.sort(elenco);

	for (int i=0; i<elenco.length; i++) {
		System.out.println(elenco[i]);
	}
	Esempio[] e = new Esempio[2];
	e[0] = new Esempio("prova2");
	e[1] = new Esempio("prova1");
	Arrays.sort(e);  // ERRORE!!! Exception in thread "main" java.lang.ClassCastException:
					 // class Esempio cannot be cast to class java.lang.Comparable
	for (int i=0; i<e.length; i++) {
			System.out.println(e[i]);
	}
   }
}

// se voglio ordinare oggetti, la classe a cui appartengono deve implementare Comparable
class Esempio {
	String nome;

	public Esempio(String nome) {
		this.nome = nome;}}
