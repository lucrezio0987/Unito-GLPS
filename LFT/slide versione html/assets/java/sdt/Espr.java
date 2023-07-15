// Traduzione di espressioni in forma infissa nel loro valore

// Attributi

// E.v, T.v, F.v = valore di E, T, F
// E'.e = somma dei termini a sinistra di E'
// E'.s = somma dei termini a sinistra di, e generati da E'
// T'.e = prodotto dei fattori a sinistra di T'
// T'.s = prodotto dei fattori a sinistra di, e generati da T'

// Schema di traduzione

// E  → T { E'.e = T.v } E' { E.v = E'.s }
// E' → { E'.s = E'.e }
// E' → +T { E₁'.e = E'.e + T.v } E₁' { E'.s = E₁'.s }
// T  → F { T'.e = F.v } T' { T.v = T'.s }
// T' → { T'.s = T'.e }
// T' → *F { T₁'.e = T'.e * F.v } T₁' { T'.s = T₁'.s }
// F  → n { F.v = n.v }
// F  → (E) { F.v = E.v }

public class Espr extends Parser {
    protected void start() {
	System.out.println(E());
    }

    private int E() {
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
	    int Tv = T();
	    int EEs = EE(Tv);
	    return EEs;
	}
	default:
	    throw error("E");
	}
    }

    private int EE(int EEe) { // EE = E'
	switch (peek()) {
	case '+': {
	    // E → +TE'
	    match('+');
	    int Tv = T();
	    int EEs = EE(EEe + Tv);
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

    private int T() {
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
	    int Fv = F();
	    int TTs = TT(Fv);
	    return TTs;
	}
	default:
	    throw error("T");
	}
    }

    private int TT(int TTe) {
	switch (peek()) {
	case '*': {
	    // T' → *FT'
	    match('*');
	    int Fv = F();
	    int TTs = TT(TTe * Fv);
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

    private int F() {
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
	    return n - '0';
	}
	case '(': {
	    // F → (E)
	    match('(');
	    int Ev = E();
	    match(')');
	    return Ev;
	}
	default:
	    throw error("F");
	}
    }

    public static void main(String[] args) {
	new Espr().parse(args[0]);
    }
}
