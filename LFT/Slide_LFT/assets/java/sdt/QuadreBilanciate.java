public class QuadreBilanciate extends Parser {
    protected void start() {
	System.out.println(S());
    }

    private int S() {
	switch (peek()) {
	case '[': { // S → [S]S
	    match('[');
	    int m = S();
	    match(']');
	    int n = S();
	    return Math.max(m + 1, n);
	}
	case '$':
	case ']': // S → ε
	    return 0;
	default:
	    throw error("S");
	}
    }

    public static void main(String[] args) {
	new QuadreBilanciate().parse(args[0]);
    }
}
