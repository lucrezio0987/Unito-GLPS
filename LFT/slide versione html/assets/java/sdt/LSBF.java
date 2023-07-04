// Traduzione di sequenze di bit con bit meno significativo a
// sinistra nel numero decimale corrispondente

// Attributi

// S.v, L.v = valore della sequenza di bit
// B.v = valore del bit

// Schema di traduzione

// S → B L { S.v = B.v + 2 × L.v }
// L → { L.v = 0 }
// L → B L { L.v = B.v + 2 × L.v }
// B → 0 { B.v = 0 }
// B → 1 { B.v = 1 }

public class LSBF extends Parser {
    protected void start() {
	System.out.println(S());
    }

    private int S() {
	switch (peek()) {
	case '0':
	case '1': {
	    // S → BL
	    int Bv = B();
	    int Lv = L();
	    return Bv + 2 * Lv;
	}
	default:
	    throw error("S");
	}
    }

    private int L() {
	switch (peek()) {
	case '0':
	case '1': {
	    // L → BL
	    int Bv = B();
	    int Lv = L();
	    return Bv + 2 * Lv;
	}
	case '$':
	    // L → ε
	    return 0;
	default:
	    throw error("L");
	}
    }

    private int B() {
	switch (peek()) {
	case '0':
	    // B → 0
	    match('0');
	    return 0;
	case '1':
	    // B → 1
	    match('1');
	    return 1;
	default:
	    throw error("B");
	}
    }

    public static void main(String[] args) {
	new LSBF().parse(args[0]);
    }
}
