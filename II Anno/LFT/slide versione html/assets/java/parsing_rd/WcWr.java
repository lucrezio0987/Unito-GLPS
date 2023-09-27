public class WcWr extends Parser {
    protected void S() {
	switch (peek()) {
	case '0':
	    // S → 0S0
	    match('0');
	    S();
	    match('0');
	    break;
	case '1':
	    // S → 1S1
	    match('1');
	    S();
	    match('1');
	    break;
	case 'c':
	    // S → c
	    match('c');
	    break;
	default:
	    error("S");
	}
    }

    public static void main(String[] args) {
	new WcWr().parse(args[0]);
    }
}
