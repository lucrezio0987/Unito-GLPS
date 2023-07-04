public class AnBn extends Parser {
    protected void S() {
	switch (peek()) {
	case 'a':
	    // S → aSb
	    match('a');
	    S();
	    match('b');
	    break;
	case '$':
	case 'b':
	    // S → ε
	    break;
	default:
	    error("S");
	}
    }

    public static void main(String[] args) {
	new AnBn().parse(args[0]);
    }
}
