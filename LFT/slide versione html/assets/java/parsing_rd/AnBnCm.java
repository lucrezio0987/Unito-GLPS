public class AnBnCm extends Parser {
    protected void S() {
	switch (peek()) {
	case 'a':
	case 'c':
	case '$':
	    // S → XC
	    X();
	    C();
	    break;
	default:
	    error("S");
	}
    }

    protected void X() {
	switch (peek()) {
	case 'a':
	    // X → aXb
	    match('a');
	    X();
	    match('b');
	    break;
	case 'b':
	case 'c':
	case '$':
	    // X → ε
	    break;
	default:
	    error("X");
	}
    }

    protected void C() {
	switch (peek()) {
	case 'c':
	    // C → cC
	    match('c');
	    C();
	    break;
	case '$':
	    // C → ε
	    break;
	default:
	    error("C");
	}
    }

    public static void main(String[] args) {
	new AnBnCm().parse(args[0]);
    }
}
