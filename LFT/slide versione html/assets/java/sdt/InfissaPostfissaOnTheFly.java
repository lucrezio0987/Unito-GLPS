// Traduzione "on-the-fly" di espressioni dalla forma infissa a
// quella postfissa

// Schema di traduzione

// E  → T E'
// E' → ε
// E' → +T { emit("+") } E₁'
// T  → F T'
// T' → ε
// T' → *F { emit("*") } T₁'
// F  → n { emit(n.v) }
// F  → (E)

public class InfissaPostfissaOnTheFly extends Parser {
    protected void start() {
	E();
	System.out.println();
    }

    // metodo per "emettere" una parte di output
    private void emit(char c) {
	System.out.print(c);
    }

    private void E() {
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
	case '9':
	case '(':
	    // E → TE'
	    T();
	    EE();
	    break;
	default:
	    throw error("E");
	}
    }

    private void EE() {
	switch (peek()) {
	case '+':
	    // E' → +TE'
	    match('+');
	    T();
	    emit('+');
	    EE();
	    break;
	case ')':
	case '$':
	    // E' → ε
	    break;
	default:
	    throw error("E'");
	}
    }

    private void T() {
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
	case '9':
	    // T → FT'
	    F();
	    TT();
	    break;
	default:
	    throw error("T");
	}
    }

    private void TT() {
	switch (peek()) {
	case '*':
	    // T' → *FT'
	    match('*');
	    F();
	    emit('*');
	    TT();
	    break;
	case '+':
	case ')':
	case '$':
	    // T' → ε
	    break;
	default:
	    throw error("T'");
	}
    }

    private void F() {
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
	case '9':
	    // F → digit
	    char c = peek();
	    match();
	    emit(c);
	    break;
	case '(':
	    // F → (E)
	    match('(');
	    E();
	    match(')');
	    break;
	default:
	    throw error("F");
	}
    }

    public static void main(String[] args) {
	new InfissaPostfissaOnTheFly().parse(args[0]);
    }
}
