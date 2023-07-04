// La grammatica deve essere fattorizzata e, dopo questa
// trasformazione, diventa:
//
// B  → CB'
// B' → ∨B | ε
// C  → DC'
// C' → ∧C | ε
// D  → t | f | ¬D | (B)
//
// Dal codice qui sotto si possono evincere gli insiemi guida di
// tutte le produzioni. La grammatica modificata risulta essere
// LL(1)

public class FormuleBooleane extends Parser {
    public void S() {
	B();
    }

    protected void B() {
	switch (peek()) {
	case 't':
	case 'f':
	case '~':
	case '(': // B → CB'
	    C();
	    B1();
	    break;
	default:
	    throw error("B");
	}
    }

    protected void B1() {
	switch (peek()) {
	case '|': // B' → ∨B
	    match('|');
	    B();
	    break;
	case '$':
	case ')': // B' → ε
	    break;
	default:
	    throw error("B'");
	}
    }

    protected void C() {
	switch (peek()) {
	case 't':
	case 'f':
	case '~':
	case '(': // C → DC'
	    D();
	    C1();
	    break;
	default:
	    throw error("C");
	}
    }

    protected void C1() {
	switch (peek()) {
	case '&': // C' → ∧C
	    match('&');
	    C();
	    break;
	case '$':
	case '|':
	case ')':
	    break;
	default:
	    throw error("C'");
	}
    }

    protected void D() {
	switch (peek()) {
	case 't': // D → t
	case 'f': // D → f
	    match();
	    break;
	case '~': // D → ¬D
	    match('~');
	    D();
	    break;
	case '(': // D → (B)
	    match('(');
	    B();
	    match(')');
	    break;
	default:
	    throw error("D");
	}
    }

    public static void main(String[] args) {
	new FormuleBooleane().parse(args[0]);
    }
}
