import java.util.*;

public class KernelModulo {

    public static void main(String[] args) {
		String[] lista = {"uno", "due", "tre"};
		for (int i=0; i<lista.length; i++) {
			System.out.println(lista[i]);
		}
		System.out.println();
		Arrays.sort(lista);
		for (int i=0; i<lista.length; i++) {
					System.out.println(lista[i]);
		}

		MioTipo[] lista2 = {new MioTipo("domani", "a"), new MioTipo("ieri", "b"),
		new MioTipo("dopodomani", "c")};
		System.out.println();
		for (int i=0; i<lista2.length; i++) {
							System.out.println(lista2[i]);
		}
		Arrays.sort(lista2);
		System.out.println();
				for (int i=0; i<lista2.length; i++) {
									System.out.println(lista2[i]);
		}
}
}

class MioTipo implements Comparable {
		private String dato;
		private String dato2;

		public MioTipo(String d, String d2) {
			this.dato = d;
			dato2 = d2;
			}

// la definizione di compareTo decide il criterio di ordinamento e posso cambiarla per ordinare per prima, o seconda componente
		public int compareTo(Object object) {
			MioTipo o = (MioTipo)object;
			return dato.compareTo(o.dato);
			// return dato2.compareTo(o.dato2);

		}

		public String toString() {
			return dato + " " + dato2;
		}
}

