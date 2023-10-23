package model;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Model extends Observable {
    private final List<String> proverbi;
    private int index;
    private final Random r;

    public Model(){
        this.proverbi = getSomeProverbs();
        index = 0;
        r = new Random();
    }
    public void chooseProverb(){
        index = r.nextInt(proverbi.size());
        setChanged();
        notifyObservers();
    }

    public String getProverb(){
        if(0 <= index && index <= proverbi.size())
            return proverbi.get(index);
        else
            return "";

    }
    private static ArrayList<String> getSomeProverbs(){
        return new ArrayList<>(List.of("A caval donato non si guarda in bocca",
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
