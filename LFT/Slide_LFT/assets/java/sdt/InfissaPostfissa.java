// Traduzione di espressioni dalla forma infissa a quella postfissa

// Attributi

// E.post, T.post, F.post = forma postfissa di E, T, F
// E'.e = forma postfissa dei termini a sinistra di E'
// E'.s = forma postfissa dei termini a sinistra di, e generati da E'
// T'.e = forma postfissa dei fattori a sinistra di T'
// T'.s = forma postfissa dei fattori a sinistra di, e generati da T'

// Schema di traduzione

// E  → T { E'.e = T.post } E' { E.post = E'.s }
// E' → { E'.s = E'.e }
// E' → +T { E₁'.e = E'.e ‖ T.post ‖ "+" } E₁' { E'.s = E₁'.s }
// T  → F { T'.e = F.post } T' { T.post = T'.s }
// T' → { T'.s = T'.e }
// T' → *F { T₁'.e = T'.e ‖ F.post ‖ "*" } T₁' { T'.s = T₁'.s }
// F  → n { F.post = n.v }
// F  → (E) { F.post = E.post }

// Nota

// L'attributo .post è principale

public class InfissaPostfissa extends Parser {
    protected void start() {
	System.out.println(E());
    }

    private String E() {
	switch (peek()) {
	case '(':
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
	    // E → TE'
	    String Tpost = T();
	    String EEs = EE(Tpost);
	    return EEs;
	}
	default:
	    throw error("E");
	}
    }

    private String EE(String EEe) { // EE = E'
	switch (peek()) {
	case '+': {
	    // E → +TE'
	    match('+');
	    String Tpost = T();
	    String EEs = EE(EEe + Tpost + "+");
	    return EEs;
	}
	case ')':
	case '$':
	    // E' → ε
	    return EEe;
	default:
	    throw error("E'");
	}
    }

    private String T() {
	switch (peek()) {
	case '(':
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
	    // T → FT'
	    String Fpost = F();
	    String TTs = TT(Fpost);
	    return TTs;
	}
	default:
	    throw error("T");
	}
    }

    private String TT(String TTe) {
	switch (peek()) {
	case '*': {
	    // T' → *FT'
	    match('*');
	    String Fpost = F();
	    String TTs = TT(TTe + Fpost + "*");
	    return TTs;
	}
	case '$':
	case ')':
	case '+':
	    // T' → ε
	    return TTe;
	default:
	    throw error("T'");
	}
    }

    private String F() {
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
	case '9': {
	    // F → n
	    char n = peek();
	    match(n);
	    return String.valueOf(n);
	}
	case '(': {
	    // F → (E)
	    match('(');
	    String Epost = E();
	    match(')');
	    return Epost;
	}
	default:
	    throw error("F");
	}
    }

    public static void main(String[] args) {
	new InfissaPostfissa().parse(args[0]);
    }
}
