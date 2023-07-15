// Traduzione di espressioni dalla forma prefissa a quella infissa,
// con minimizzazione delle parentesi

// Attributi

// S.i, E.i = forma infissa di S, E
// E.c = insieme di operatori che, se trovati in E, vanno protetti da parentesi

// Schema di traduzione

// S →   { E.c = ∅ }    E  { S.i = E.i }
// E → + { E₁.c = ∅ }   E₁ { E₂.c = {+} }   E₂ { E.i = par(+, E₁.i ‖ "+" ‖ E₂.i) }
// E → * { E₁.c = {+} } E₁ { E₂.c = {+,*} } E₂ { E.i = par(*, E₁.i ‖ "*" ‖ E₂.i) }
// E → n { E.i = n.v }

public class PrefissaInfissa extends Parser {
    protected void start() {
	System.out.println(S());
    }

    private String S() {
	switch (peek()) {
	case '+':
	case '*':
	case '0':
	case '1':
	case '2':
	case '3':
	case '4':
	case '5':
	case '6':
	case '7':
	case '8':
	case '9': {
	    // S → E
	    String Ei = E(""); // "" = ∅
	    return Ei;
	}
	default:
	    throw error("S");
	}
    }

    private String E(String Ec) {
	switch (peek()) {
	case '+': {
	    // E → +E₁E₂
	    match('+');
	    String E1i = E("");
	    String E2i = E("+");
	    return par(Ec.indexOf('+'), E1i + "+" + E2i);
	}
	case '*': {
	    // E → *E₁E₂
	    match('*');
	    String E1i = E("+");
	    String E2i = E("+*");
	    return par(Ec.indexOf('*'), E1i + "*" + E2i);
	}
	case '0':
	case '1':
	case '2':
	case '3':
	case '4':
	case '5':
	case '6':
	case '7':
	case '8':
	case '9': {
	    // E → n
	    char n = peek();
	    match(n);
	    return String.valueOf(n);
	}
	default:
	    throw error("E");
	}
    }

    private static String par(int i, String s) {
	return i >= 0 ? "(" + s + ")" : s;
    }

    public static void main(String[] args) {
	new PrefissaInfissa().parse(args[0]);
    }
}
