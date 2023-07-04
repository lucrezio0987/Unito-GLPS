// Per visualizzare il bytecode in formato mnemonico:
// 1) compilare:   javac Euclide.java
// 2) decompilare: javap -c Euclide

public class Euclide {
    public static int mcd(int a, int b) {
	while (b != 0) {
	    int t = b;
	    b = a % b;
	    a = t;
	}
	return a;
    }

    public static void main(String[] args) {
	int a = Integer.parseInt(args[0]);
	int b = Integer.parseInt(args[1]);
	System.out.println(mcd(a, b));
    }
}
