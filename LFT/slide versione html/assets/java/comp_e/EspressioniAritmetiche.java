// E  → TE'
// E' → ε | +TE' | -TE'
// T  → FT'
// T' → ε | *FT' | /FT' | %FT'
// F  → n | (E)

public class EspressioniAritmetiche extends Compiler {
    protected void start() {
	E();
    }

    private void E() {
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
	case '(': // E → TE'
	    T();
	    EE();
	    break;
	default:
	    throw error("E");
	}
    }

    private void EE() {
	switch (peek()) {
	case '+': // E' → +TE'
	    check('+');
	    T();
	    emit("iadd");
	    EE();
	    break;
	case '-': // E' → -TE'
	    check('-');
	    T();
	    emit("isub");
	    EE();
	    break;
	case ')':
	case '$': // E' → ε
	    break;
	default:
	    throw error("E'");
	}
    }

    private void T() {
	switch (peek()) {
	case '(':
	case '0':
	case '1':
	case '2':
	case '3':
	case '4':
	case '5':
	case '6':
	case '7':
	case '8':
	case '9': // T → FT'
	    F();
	    TT();
	    break;
	default:
	    throw error("T");
	}
    }

    private void TT() {
	switch (peek()) {
	case '*': // T' → *FT'
	    check('*');
	    F();
	    emit("imul");
	    TT();
	    break;
	case '/': // T' → /FT'
	    check('/');
	    F();
	    emit("idiv");
	    TT();
	    break;
	case '%': // T' → %FT'
	    check('%');
	    F();
	    emit("irem");
	    TT();
	    break;
	case '+':
	case '-':
	case ')':
	case '$': // T' → ε
	    break;
	default:
	    throw error("T'");
	}
    }

    private void F() {
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
	case '9': // F → digit
	    int v = peek() - '0';
	    check();
	    emit("ldc " + v);
	    break;
	case '(': // F → (E)
	    check('(');
	    E();
	    check(')');
	    break;
	default:
	    throw error("F");
	}
    }

    public static void main(String[] args) {
	new EspressioniAritmetiche().parse(args[0]);
    }
}
