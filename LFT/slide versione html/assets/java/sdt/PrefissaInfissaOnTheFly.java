// Traduzione on-the-fly di espressioni dalla forma prefissa a
// quella infissa, con minimizzazione delle parentesi

// Attributi

// E.c = insieme di operatori che, se trovati in E, vanno protetti
// da parentesi

// Schema di traduzione

// S →    { E.c = ∅ }
//     E
// E → +  { if (+ ∈ E.c) emit("(") }
//        { E₁.c = ∅ }
//     E₁ { emit("+") }
//        { E₂.c = {+} }
//     E₂ { if (+ ∈ E.c) emit(")") }
// E → *  { if (* ∈ E.c) emit("(") }
//        { E₁.c = {+} }
//     E₁ { emit("*") }
//        { E₂.c = {+,*} }
//     E₂ { if (* ∈ E.c) emit(")") }
// E → n  { E.i = n.v }

public class PrefissaInfissaOnTheFly extends Parser {
    protected void start() {
	S();
	System.out.println("");
    }

    private void emit(String s) {
	System.out.print(s);
    }

    private void S() {
	switch (peek()) {
	case '+':
	case '*':
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
	    // S → E
	    E(""); // "" = ∅
	    break;
	default:
	    throw error("S");
	}
    }

    private void E(String Ec) {
	switch (peek()) {
	case '+':
	    // E → +E₁E₂
	    match('+');
	    if (Ec.indexOf('+') >= 0) emit("(");
	    E("");
	    emit("+");
	    E("+");
	    if (Ec.indexOf('+') >= 0) emit(")");
	    break;
	case '*':
	    // E → *E₁E₂
	    match('*');
	    if (Ec.indexOf('*') >= 0) emit("(");
	    E("+");
	    emit("*");
	    E("+*");
	    if (Ec.indexOf('*') >= 0) emit(")");
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
	new PrefissaInfissaOnTheFly().parse(args[0]);
    }
}
