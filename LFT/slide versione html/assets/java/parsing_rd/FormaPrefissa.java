public class FormaPrefissa extends Parser {
    protected void S() {
	E();
    }

    protected void E() {
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
	    // E → 0 | 1 | ⋯ | 9
	    match();
	    break;
	case '+':
	    // E → +EE
	    match('+');
	    E();
	    E();
	    break;
	case '*':
	    // E → *EE
	    match('*');
	    E();
	    E();
	    break;
	default:
	    error("E");
	}
    }

    public static void main(String[] args) {
	new FormaPrefissa().parse(args[0]);
    }
}
