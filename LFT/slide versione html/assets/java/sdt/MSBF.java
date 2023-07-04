// Traduzione di sequenze di bit con bit più significativo a
// sinistra nel numero decimale corrispondente

public class MSBF extends Parser {
    private static class LAttributes {
	public int val; // L.val = valore della sequenza di bit
	public int len; // L.len = lunghezza della sequenza di bit

	public LAttributes(int val, int len) {
	    this.val = val;
	    this.len = len;
	}
    }

    protected void start() {
	System.out.println(L().val);
    }

    private LAttributes L() {
	switch (peek()) {
	case '0':
	case '1': {
	    // L → BL
	    int bit = B();
	    LAttributes Las = L();
	    return new LAttributes((1 << Las.len) * bit + Las.val, Las.len + 1);
	}
	case '$':
	    // L → ε
	    return new LAttributes(0, 0);
	default:
	    throw error("L");
	}
    }

    private int B() {
	switch (peek()) {
	case '0':
	    match('0');
	    return 0;
	case '1':
	    match('1');
	    return 1;
	default:
	    throw error("B");
	}
    }

    public static void main(String[] args) {
	new MSBF().parse(args[0]);
    }
}
