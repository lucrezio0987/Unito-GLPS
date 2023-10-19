package es2.model;

import java.util.*;

public class Model extends Observable {
    private final List<String> proverbs;
    private int index;
    private final Random r;

    public Model() {
        this.proverbs = getSomeProverbs();
        this.index = 0;
        this.r = new Random();
    }

    public void chooseProverb() {
        this.index = r.nextInt(this.proverbs.size());
        setChanged();
        notifyObservers();
    }

    public String getProverb() {
        if (0 <= this.index && this.index <= this.proverbs.size())
            return proverbs.get(this.index);
        else return "";
    }

    private static ArrayList<String> getSomeProverbs() {
        return new ArrayList<>(
                List.of("A caval donato non si guarda in bocca",
                        "A buon intenditor, poche parole",
                        "Chi dorme non piglia pesci",
                        "Il lupo perde il pelo ma non il vizio",
                        "L'erba del vicino è sempre più verde",
                        "Ride bene chi ride ultimo",
                        "Il buon giorno si vede dal mattino",
                        "Fra i due litiganti il terzo gode",
                        "Non è tutto oro quello che luccica")
        );
    }

}
