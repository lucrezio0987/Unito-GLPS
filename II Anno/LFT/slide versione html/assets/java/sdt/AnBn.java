// Traduzione di stringhe della forma aⁿbⁿ in n

// Attributi

// S.n = numero di a nella stringa generata da S

// Schema di traduzione

// S → { S.n = 0 }
// S → aS₁b { S.n = S₁.n + 1 }

public class AnBn extends Parser {
    protected void start() {
	System.out.println(S());
    }

    private int S() {
	switch (peek()) {
	case 'a': {
	    // S → aSb
	    match('a');
	    int Sn = S();
	    match('b');
	    return Sn + 1;
	}
	case 'b':
	case '$':
	    // S → ε
	    return 0;
	default:
	    throw error("S");
	}
    }

    public static void main(String[] args) {
	new AnBn().parse(args[0]);
    }
}
