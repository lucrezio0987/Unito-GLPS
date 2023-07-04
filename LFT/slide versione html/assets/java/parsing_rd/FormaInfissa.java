public class FormaInfissa extends Parser {
    protected void S() {
	E();
    }

    protected void E() {
	switch (peek()) {
	case 'x':
	case '(':
	    // E → TE'
	    T();
	    E1();
	    break;
	default:
	    error("E");
	}
    }

    protected void E1() {
	switch (peek()) {
	case '+':
	    // E' → +TE'
	    match();
	    T();
	    E1();
	    break;
	case ')':
	case '$':
	    // E' → ε
	    break;
	default:
	    error("E'");
	}
    }

    protected void T() {
	switch (peek()) {
	case '(':
	case 'x':
	    // T → FT'
	    F();
	    T1();
	    break;
	default:
	    error("T");
	}
    }

    protected void T1() {
	switch (peek()) {
	case '*':
	    // T' → *FT'
	    match();
	    F();
	    T1();
	    break;
	case '+':
	case ')':
	case '$':
	    // T' → ε
	    break;
	default:
	    error("T'");
	}
    }

    protected void F() {
	switch (peek()) {
	case 'x':
	    // F → x
	    match();
	    break;
	case '(':
	    // F → (E)
	    match('(');
	    E();
	    match(')');
	    break;
	default:
	    error("F");
	}
    }

    public static void main(String[] args) {
	new FormaInfissa().parse(args[0]);
    }
}
