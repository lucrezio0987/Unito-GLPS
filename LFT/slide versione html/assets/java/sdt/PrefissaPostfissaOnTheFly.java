// Traduzione "on-the-fly" di espressioni dalla forma prefissa a
// quella

// Non servono attributi

// Schema di traduzione

// E → +EE { emit "+" }
// E → *EE { emit "*" }
// E → n   { emit n.v }

public class PrefissaPostfissaOnTheFly extends Parser {
    protected void start() {
	E();
	System.out.println("");
    }

    private void emit(String s) {
	System.out.print(s);
    }

    private void E() {
	switch (peek()) {
	case '+':
	    // E → +EE
	    match('+');
	    E();
	    E();
	    emit("+");
	    break;
	case '*':
	    // E → *EE
	    match('*');
	    E();
	    E();
	    emit("*");
	    break;
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
	    // E → n
	    char n = peek();
	    match(n);
	    emit(String.valueOf(n));
	    break;
	}
	default:
	    throw error("E");
	}
    }

    public static void main(String[] args) {
	new PrefissaPostfissaOnTheFly().parse(args[0]);
    }
}
