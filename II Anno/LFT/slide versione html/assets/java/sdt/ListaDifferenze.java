// Lista delle differenze

// Attributi

// S.v = risultato della traduzione
// L.e = primo elemento della lista
// L.s = risultato della traduzione di L

// Schema di traduzione

// S → n { L.e = n.v } L { S.v = L.s }
// L → ε { L.s = [] }
// L → ; n { L₁.e = L.e } L₁ { L.s = n.v - L.e || L₁.s }

public class ListaDifferenze extends Parser {
    protected void start() {
	System.out.println(S());
    }

    private String S() {
	switch (peek()) {
	case '0':
	case '1':
	case '2':
	case '3':
	case '4':
	case '5':
	case '6':
	case '7':
	case '8':
	case '9': { // S → n L
	    int n_v = peek() - '0';
	    match(peek());
	    String L_s = L(n_v);
	    return L_s;
	}
	default:
	    throw error("S");
	}
    }

    private String L(int e) {
	switch (peek()) {
	case '$': // L → ε
	    return "";
	case ';': { // L -> ; n L
	    match(';');
	    int n_v = peek() - '0';
	    // qui andrebbe fatto un controllo più preciso sul
	    // prossimo simbolo, ma con il lexer minimale usato in
	    // questi esempi non è semplice rispettando la
	    // costruzione del traduttore vista a lezione, quindi
	    // assumo semplicemente che il prossimo carattere sia
	    // una cifra compresa tra 0 e 9
	    match(peek());
	    String L_s = L(e);
	    return Integer.toString(n_v - e) + ";" + L_s;
	}
	default:
	    throw error("L");
	}
    }

    public static void main(String[] args) {
	new ListaDifferenze().parse(args[0]);
    }
}
