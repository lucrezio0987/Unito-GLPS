// traduce espressioni aritmetiche dalla forma infissa a quella
// prefissa

public class InfissaPrefissa extends Parser {
    protected void start() {
	System.out.println(E());
    }

    private String E() {
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
	case '(': {
	    // E → TE'
	    String v = T();
	    String s = E1(v);
	    return s;
	}
	default:
	    throw error("E");
	}
    }

    private String E1(String e) {
	switch (peek()) {
	case '+': {
	    // E' → +TE'
	    match('+');
	    String v = T();
	    String s = E1("+" + e + v);
	    return s;
	}
	case ')':
	case '$':
	    // E' → ε
	    return e;
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
	    String v = F();
	    String s = T1(v);
	    return s;
	}
	default:
	    throw error("T");
	}
    }

    private String T1(String e) {
	switch (peek()) {
	case '*': {
	    // T' → *FT'
	    match('*');
	    String v = F();
	    String s = T1("*" + e + v);
	    return s;
	}
	case '+':
	case ')':
	case '$':
	    // T' → ε
	    return e;
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
	    // F → digit
	    String v = String.valueOf(peek());
	    match();
	    return v;
	}
	case '(': {
	    // F → (E)
	    match('(');
	    String v = E();
	    match(')');
	    return v;
	}
	default:
	    throw error("F");
	}
    }

    public static void main(String[] args) {
	new InfissaPrefissa().parse(args[0]);
    }
}
