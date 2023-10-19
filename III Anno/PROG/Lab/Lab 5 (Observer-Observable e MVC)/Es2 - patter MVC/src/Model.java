import java.util.Observable;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Model extends Observable {
    String[] proverbi = {"ciao", "come", "stai"};
    int index;

    public String getProverio() {
        return proverbi[index];
    }

    public void choseProverbio() {
        index = RandomGenerator.getDefault().nextInt(0, proverbi.length - 1);
        setChanged();
        notifyObservers();
    }

}
