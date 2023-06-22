public abstract class Compiler extends Parser {
    private int nextLabel;        // numero della prossima etichetta
    private boolean pendingLabel; // true se è stata emessa un'etichetta
                                  // ma non ancora l'istruzione che la segue

    public Compiler() {
	nextLabel = 0;
	pendingLabel = false;
    }

    // il metodo emit produce una istruzione di codice
    protected void emit(String code) {
	System.out.println("\t" + code);
	pendingLabel = false;
    }

    // il metodo newlabel() genera una nuova etichetta
    protected String newlabel() {
	return "L" + nextLabel++;
    }

    // il metodo emitlabel produce una etichetta
    protected void emitlabel(String label) {
	if (pendingLabel) System.out.println("");
	System.out.print(label + ":");
	pendingLabel = true;
    }
}
