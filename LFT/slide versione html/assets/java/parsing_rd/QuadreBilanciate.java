public class QuadreBilanciate extends Parser {
    protected void S() {
	switch (peek()) {
	case '[':
	    // S → [S]S
	    match('[');
	    S();
	    match(']');
	    S();
	    break;
	case '$':
	case ']':
	    // S → ε
	    break;
	default:
	    error("S");
	}
    }

    public static void main(String[] args) {
	new QuadreBilanciate().parse(args[0]);
    }
}
