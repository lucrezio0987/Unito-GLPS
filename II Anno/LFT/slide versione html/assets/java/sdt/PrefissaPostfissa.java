// Traduzione di espressioni dalla forma prefissa a quella postfissa

// Attributi

// E.p = forma postfissa di E

// Schema di traduzione

// E → +E₁E₂ { E.p = E₁.p ‖ E₂.p ‖ "+" }
// E → *E₁E₂ { E.p = E₁.p ‖ E₂.p ‖ "*" }
// E → n     { E.p = n.v }

public class PrefissaPostfissa extends Parser {
    protected void start() {
	System.out.println(E());
    }

    private String E() {
	switch (peek()) {
	case '+': {
	    // E → +E₁E₂
	    match('+');
	    String E1p = E();
	    String E2p = E();
	    return E1p + E2p + "+";
	}
	case '*': {
	    // E → *E₁E₂
	    match('*');
	    String E1p = E();
	    String E2p = E();
	    return E1p + E2p + "*";
	}
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
	    return String.valueOf(n);
	}
	default:
	    throw error("E");
	}
    }

    public static void main(String[] args) {
	new PrefissaPostfissa().parse(args[0]);
    }
}
